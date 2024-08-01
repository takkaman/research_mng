<template>
    <el-container class="main-container">
        <el-aside v-bind:class="asideClass">
            <LeftNav :activeIndex="selectedIndex"></LeftNav>
        </el-aside>
        <el-container>
            <el-header class="main-header">
                <TopNav></TopNav>
            </el-header>
            <el-main class="main-center">
                <div class="Book" style="padding: 30px;">
                    <!-- 输入框搜索 -->
                    <el-form :model="searchResearcher" :inline="true" class="demo-form-inline">
                        <el-form-item label="职工号 : ">
                            <el-input v-model="searchResearcher.id" placeholder="职工号"></el-input>
                        </el-form-item>
                        <el-form-item label="姓名 : ">
                            <el-input v-model="searchResearcher.name" placeholder="姓名"></el-input>
                        </el-form-item>
                        <el-form-item label="职称 : ">
                            <el-input v-model="searchResearcher.title" placeholder="职称"></el-input>
                        </el-form-item>
                        <el-form-item label="一级学科 : ">
                            <el-input v-model="searchResearcher.subject" placeholder="一级学科"></el-input>
                        </el-form-item>
                        <el-form-item label="最高学位 : ">
                            <el-input v-model="searchResearcher.degree" placeholder="最高学位"></el-input>
                        </el-form-item>
                        <el-form-item label="所属单位 : ">
                            <el-input v-model="searchResearcher.department" placeholder="所属单位"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" icon="el-icon-search" plain @click="doSearch()">查询</el-button>
                            <el-button v-if="userType == 1" type="primary" icon="el-icon-circle-plus-outline" plain
                                @click="dialogFormVisible = true">新增</el-button>
                                <el-button type="primary" icon="el-icon-download" plain @click="exportToExcel">导出</el-button>
                        </el-form-item>
                    </el-form>
                    <!-- 科研项目表格 -->
                    <el-table :data="tableData" style="width: 100%">
                        <el-table-column prop="id" label="职工号"></el-table-column>
                        <el-table-column prop="name" label="姓名"></el-table-column>
                        <el-table-column prop="title" label="职称"></el-table-column>
                        <el-table-column prop="subject" label="一级学科"></el-table-column>
                        <el-table-column prop="degree" label="最高学位"></el-table-column>
                        <el-table-column label="科研详情">
                            <template slot-scope="scope">
                                <a :href="handleLink(scope.row.id)">
                                    详情
                                </a>
                            </template>
                        </el-table-column>
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
                    <el-dialog title="新增 科研人员" :visible.sync="dialogFormVisible" @close="doClear()">
                        <el-form :model="researcher" :rules="rules" ref="researcher">
                            <el-form-item label="职工号" :label-width="formLabelWidth" prop="id">
                                <el-input v-model="researcher.id" autocomplete="off"
                                    :disabled="disabledFormItems.researcherId"></el-input>
                            </el-form-item>
                            <el-form-item label="姓名" :label-width="formLabelWidth" prop="id">
                                <el-input v-model="researcher.name" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="职称" :label-width="formLabelWidth" prop="name">
                                <el-input v-model="researcher.title" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="一级学科" :label-width="formLabelWidth" prop="press">
                                <el-input v-model="researcher.subject" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="最高学位" :label-width="formLabelWidth" prop="authors">
                                <el-input v-model="researcher.degree" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="所属单位" :label-width="formLabelWidth" prop="publishTime">
                                <el-select v-model="researcher.department" placeholder="请选择">
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
import { getUserId, getUserType } from "@/utils/auth";
import * as XLSX from 'xlsx';

// 导出模块
export default {
    components: {
        TopNav, LeftNav
    },
    data() {
        return {
            userId: getUserId(),
            userType: getUserType(),
            selectedIndex: '/researchers',
            tableData: [],
            rows: 10,
            total: 0,
            page: 1,
            // 是否打开弹窗
            dialogFormVisible: false,
            // 弹窗标题
            title: '新增页面',
            // 定义数组接收数据
            researcher:
                { id: '', name: '', title: '', subject: '', degree: '', department: '' },
            searchResearcher:
                { currentPage: 1, pageSize: 10, id: '', name: '', title: '', subject: '', degree: '', department: '' },
            // 类型
            types: [],
            // 输入框长度
            formLabelWidth: '100px',
            //表单验证
            rules: {
                //定义验证格式
                id: [
                    { required: true, message: '请输入职工号', trigger: 'blur' },
                    // { min: 1, max: 10, message: '长度在 1 到 10 个数字', trigger: 'blur' }
                ],
                name: [
                    { required: true, message: '请输入著作名称', trigger: 'change' },
                    { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
                ]
            },
            disabledFormItems: {
                researcherId: false,
            },
            asideClass: 'main-aside',
            filename: 'research_info'
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
                researcherId: false
            };
            this.$refs.researcher.resetFields();
            this.researcher = this.$options.data.call(this).researcher;
        },
        handleLink(id) {
            console.log("go to details");
            // this.$router.push({ path: `/researcher/details/${id}` });
            return `/researcher/details/${id}`;
        },
        // 编辑
        handleEdit(index, row) {
            this.dialogFormVisible = true;
            this.disabledFormItems = {
                researcherId: true
            }
            if (row) {
                this.title = '编辑 学术著作';
                this.researcher.id = row.id;
                this.researcher.name = row.name;
                this.researcher.title = row.title;
                this.researcher.subject = row.subject;
                this.researcher.degree = row.degree;
                this.researcher.department = row.department;
            }
        },
        // 删除
        handleDelete(index, row) {
            this.$confirm("确定删除吗?", "提示", {
                confirmButtonText: "确定",         // 确认按钮文本
                cancelButtonText: "取消",          // 取消按钮文本
                type: "warning",                   // 提示类型（警告）
            }).then(async () => {
                let res = await http.post("/researcher/delete/" + row.id);
                if (res && res.code == 200) {
                    this.$message.success("删除成功");
                    this.fetch();
                }
            });
        },
        // 提交
        doSubmit() {
            console.log("do submit");
            //表单验证
            this.$refs['researcher'].validate(async (valid) => {
                if (valid) {
                    //验证通过执行增加修改方法
                    console.log(this.researcher);

                    //获取后台请求书籍数据的地址
                    let res = await http.post("/researcher/upsert", this.researcher);
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
            let res = await http.get("/researcher/list");
            if (res && res.code == 200) {
                // console.log(res.data);
                this.tableData = res.data;
            }
        },
        async doSearch() {
            console.log("do search");
            let res = await http.get("/researcher/search", this.searchResearcher);
            if (res && res.code == 200) {
                console.log(res.data);
                this.tableData = res.data.records;
            }
        },
        exportToExcel() {
            const worksheet = XLSX.utils.json_to_sheet(this.tableData);
            const workbook = XLSX.utils.book_new();
            XLSX.utils.book_append_sheet(workbook, worksheet, 'Data');
            const excelBuffer = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });

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