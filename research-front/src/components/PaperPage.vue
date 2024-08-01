<template>
    <el-container class="main-container">
        <el-aside v-bind:class="asideClass">
            <LeftNav :activeIndex="selectedIndex"></LeftNav>
        </el-aside>
        <el-container>
            <el-header class="main-header">
                <TopNav></TopNav>
            </el-header>
            <el-main class="main-center" v-if="userId">
                <div class="Book" style="padding: 30px;">
                    <!-- 输入框搜索 -->
                    <el-form :model="searchWork" :inline="true" class="demo-form-inline">
                        <!-- <el-form-item label="论文编号 : ">
                            <el-input v-model="searchWork.id" placeholder="论文编号"></el-input>
                        </el-form-item> -->
                        <el-form-item label="论文名称 : ">
                            <el-input v-model="searchWork.name" placeholder="论文名称"></el-input>
                        </el-form-item>
                        <el-form-item label="发表/刊物论文集 : ">
                            <el-input v-model="searchWork.press" placeholder="发表/刊物论文集"></el-input>
                        </el-form-item>
                        <el-form-item label="所有作者 : ">
                            <el-input v-model="searchWork.authors" placeholder="所有作者"></el-input>
                        </el-form-item>
                        <el-form-item label="发表/出版时间 : ">
                            <el-input v-model="searchWork.publishTime" placeholder="发表/出版时间"></el-input>
                        </el-form-item>
                        <el-form-item label="所属单位 : ">
                            <el-input v-model="searchWork.department" placeholder="所属单位"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" icon="el-icon-search" plain @click="doSearch()">查询</el-button>
                            <el-button v-if="userType == 1" type="primary" icon="el-icon-circle-plus-outline" plain
                                @click="dialogFormVisible = true">新增</el-button>
                            <el-button type="primary" icon="el-icon-download" plain @click="exportToExcel">导出</el-button>
                        </el-form-item>
                    </el-form>
                    <!-- 论文表格 -->
                    <el-table :data="tableData" style="width: 100%">
                        <!-- <el-table-column prop="id" label="论文编号"></el-table-column> -->
                        <el-table-column prop="name" label="论文名称"></el-table-column>
                        <el-table-column prop="press" label="发表/出版论文集"></el-table-column>
                        <el-table-column prop="authors" label="所有作者"></el-table-column>
                        <el-table-column prop="publishTime" label="发表时间"></el-table-column>
                        <el-table-column prop="department" label="所属单位"></el-table-column>
                        <el-table-column v-if="userType == 1" label="操作" min-width="180">
                            <template slot-scope="scope">
                                <el-button size="mini" icon="el-icon-edit-outline" type="warning"
                                    @click="handleEdit(scope.$index, scope.row)">编 辑
                                </el-button>
                                <el-button size="mini" icon="el-icon-delete" type="danger"
                                    @click="handleDelete(scope.$index, scope.row)">删
                                    除
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <!-- 分页 -->
                    <div class="block" style="padding: 20px;">
                        <el-pagination @size-change="handleSizeChange()" @current-change="handleCurrentChange()"
                            :current-page="page" background :page-sizes="[10, 20, 30, 40]" :page-size="rows"
                            layout="total, sizes, prev, pager, next, jumper" :total="total">
                        </el-pagination>
                    </div>
                    <!-- 弹窗 -->
                    <el-dialog title="新增 学术论文" :visible.sync="dialogFormVisible" @close="doClear()">
                        <el-form :model="paper" :rules="rules" ref="paper">
                            <el-form-item v-if="disabledFormItems.paperId" label="论文编号" :label-width="formLabelWidth" prop="id">
                                <el-input v-model="paper.id" autocomplete="off"
                                    :disabled="disabledFormItems.paperId"></el-input>
                            </el-form-item>
                            <el-form-item label="论文名称" :label-width="formLabelWidth" prop="name">
                                <el-input v-model="paper.name" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="发表/出版论文集" :label-width="formLabelWidth" prop="press">
                                <el-input v-model="paper.press" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="所有作者" :label-width="formLabelWidth" prop="authors">
                                <el-input v-model="paper.authors" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="发表时间" :label-width="formLabelWidth" prop="publishTime">
                                <el-input v-model="paper.publishTime" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="所属单位" :label-width="formLabelWidth" prop="department">
                                <el-select v-model="paper.department" placeholder="请选择">
                                    <el-option label="英语系" value="英语系"></el-option>
                                    <el-option label="日语系" value="英语系"></el-option>
                                    <el-option label="法语系" value="法语系"></el-option>
                                    <el-option label="欧语系" value="欧语系"></el-option>
                                    <el-option label="外语教学部" value="外语教学部"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-form>
                        <div slot="footer" class="dialog-footer">
                            <el-button @click="dialogFormVisible = false">取 消</el-button>
                            <el-button type="primary" @click="doSubmit()">确 定</el-button>
                        </div>
                    </el-dialog>
                </div>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
// 导入组件
import TopNav from '@/components/TopNav.vue'
import LeftNav from '@/components/LeftNav.vue'
import http from "@/utils/http";
import moment from 'moment';
import * as XLSX from 'xlsx';
import { getUserId, getUserType } from "@/utils/auth";

// 导出模块
export default {
    components: {
        TopNav, LeftNav
    },
    data() {
        return {
            userId: getUserId(),
            userType: getUserType(),
            selectedIndex: '/papers',
            tableData: [],
            rows: 10,
            total: 0,
            page: 1,
            // 是否打开弹窗
            dialogFormVisible: false,
            // 弹窗标题
            title: '新增页面',
            // 定义数组接收数据
            paper:
                { id: '', name: '', press: '', authors: '', publishTime: '', department: ''},
            searchWork:
                { currentPage: 1, pageSize: 10, id: '', name: '', press: '', authors: '', publishTime: '', department: '' },
            // 类型
            types: [],
            // 输入框长度
            formLabelWidth: '100px',
            //表单验证
            rules: {
                //定义验证格式
                name: [
                    { required: true, message: '请输入著作名称', trigger: 'change' },
                ],
                press: [
                    { required: true, message: '请输入出版社', trigger: 'change' },
                ],
                authors: [
                    { required: true, message: '请输入作者', trigger: 'change' },
                ],
                publishTime: [
                    { required: true, message: '请输入发表时间', trigger: 'change' },
                ],
                department: [
                    { required: true, message: '请输入所属单位', trigger: 'change' },
                ],
                category: [
                    { required: true, message: '请选择著作类别', trigger: 'change' },
                ],
            },
            disabledFormItems: {
                paperId: false,
            },
            asideClass: 'main-aside',
            filename: 'paper_info'
        }
    },
    created() {
        this.fetch();
    },
    methods: {
        formatDate(row, column, cellValue) {
            // 使用moment.js来格式化日期
            return moment(cellValue).format('YYYY-MM-DD');
        },
        // 初始化方法
        doClear() {
            console.log("clear triggerred");
            this.dialogFormVisible = false;
            this.disabledFormItems = {
                paperId: false
            };
            this.$refs.paper.resetFields();
            this.paper = this.$options.data.call(this).paper;
        },
        // 编辑
        handleEdit(index, row) {
            this.dialogFormVisible = true;
            this.disabledFormItems = {
                paperId: false
            }
            if (row) {
                this.title = '编辑 学术论文';
                this.paper.id = row.id;
                this.paper.name = row.name;
                this.paper.press = row.press;
                this.paper.authors = row.authors;
                this.paper.publishTime = row.publishTime;
                this.paper.department = row.department;
            }
        },
        // 删除
        async handleDelete(index, row) {
            console.log(index, row.id)
            let res = await http.post("/paper/delete/" + row.id);
            if (res && res.code == 200) {
                this.$message.success("删除成功");
                this.fetch();
            }
        },
        // 提交
        doSubmit() {
            console.log("do submit");
            //表单验证
            this.$refs['paper'].validate(async (valid) => {
                if (valid) {
                    //验证通过执行增加修改方法
                    console.log(this.paper);

                    //获取后台请求书籍数据的地址
                    let res = await http.post("/paper/upsert", this.paper);
                    console.log(res);
                    if (res && res.code == 200) {
                        this.$message.success("操作成功");
                        this.fetch();
                    }
                } else {
                    this.$message.error("操作失败");
                    return false;
                }
            });
            this.dialogFormVisible = false;
        },
        handleSizeChange() {
            this.fetch();
        },
        handleCurrentChange() {
            //当前页码大小发生变化
            // console.log(params)
            this.fetch();
        },
        async fetch() {
            //获取后台请求书籍数据的地址
            console.log("do fetch");
            let res = await http.get("/paper/list");
            if (res && res.code == 200) {
                // console.log(res.data);
                this.tableData = res.data;
            }
        },
        async doSearch() {
            console.log("do search");
            let res = await http.get("/paper/search", this.searchWork);
            if (res && res.code == 200) {
                console.log(res.data);
                this.tableData = res.data.records;
            }
        },
        exportToExcel() {
            const papersheet = XLSX.utils.json_to_sheet(this.tableData);
            const paperbook = XLSX.utils.book_new();
            XLSX.utils.book_append_sheet(paperbook, papersheet, 'Data');
            const excelBuffer = XLSX.write(paperbook, { bookType: 'xlsx', type: 'array' });

            // 使用 Blob 创建一个可下载的文件对象
            const blob = new Blob([excelBuffer], { type: 'application/octet-stream' });

            // 创建一个链接元素用于下载
            const link = document.createElement('a');
            link.href = URL.createObjectURL(blob);
            link.download = `${this.filename}.xlsx`; // 用户自定义的文件名

            // 模拟点击链接以下载文件
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            URL.revokeObjectURL(link.href); // 释放对象URL
        }
    }
};
</script>
<style scoped>
.main-container {
    height: 100%;
    width: 100%;
    box-sizing: border-box;
}

.main-aside-collapsed {
    /* 在CSS中，通过对某一样式声明! important ，可以更改默认的CSS样式优先级规则，使该条样式属性声明具有最高优先级 */
    width: 64px !important;
    height: 100%;
    background-color: #334157;
    margin: 0px;
}

.main-aside {
    width: 240px !important;
    height: 100%;
    background-color: #334157;
    margin: 0px;
}

.main-header,
.main-center {
    padding: 0px;
    border-left: 2px solid #333;
}
</style>