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
                        <el-form-item label="著作编号 : ">
                            <el-input v-model="searchWork.id" placeholder="著作编号"></el-input>
                        </el-form-item>
                        <el-form-item label="著作名称 : ">
                            <el-input v-model="searchWork.name" placeholder="著作名称"></el-input>
                        </el-form-item>
                        <el-form-item label="出版社名称 : ">
                            <el-input v-model="searchWork.press" placeholder="出版社名称"></el-input>
                        </el-form-item>
                        <el-form-item label="所有作者 : ">
                            <el-input v-model="searchWork.authors" placeholder="所有作者"></el-input>
                        </el-form-item>
                        <el-form-item label="出版/发表时间 : ">
                            <el-input v-model="searchWork.publishTime" placeholder="出版/发表时间"></el-input>
                        </el-form-item>
                        <el-form-item label="著作类别 : ">
                            <el-input v-model="searchWork.publishTime" placeholder="出版/发表时间"></el-input>
                        </el-form-item>
                        <el-form-item label="所属单位 : ">
                            <el-input v-model="searchWork.department" placeholder="所属单位"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" icon="el-icon-search" plain @click="doSearch()">查询</el-button>
                            <el-button v-if="userType == 1" type="primary" icon="el-icon-circle-plus-outline" plain
                                @click="dialogFormVisible = true">新增</el-button>
                            <el-button type="primary" icon="el-icon-download" plain
                                @click="exportToExcel">导出</el-button>
                            <el-upload :action="this.uploadUrl"
                                :on-success="handleSuccess" :on-error="handleError" name="file" accept=".xls,.xlsx"
                                :show-file-list="false">
                                <el-button slot="trigger" type="primary" icon="el-icon-upload2" plain >导入</el-button>
                            </el-upload>
                            <!-- <el-button type="primary" icon="el-icon-upload2" plain @click="importExcel">导入</el-button> -->
                        </el-form-item>
                    </el-form>
                    <!-- 著作表格 -->
                    <el-table :data="tableData" style="width: 100%">
                        <el-table-column v-if="disabledFormItems.workId" prop="id" label="著作编号"
                            hidden="hidden"></el-table-column>
                        <el-table-column prop="name" label="著作名称"></el-table-column>
                        <el-table-column prop="press" label="出版社名称"></el-table-column>
                        <el-table-column prop="authors" label="所有作者"></el-table-column>
                        <el-table-column prop="publishTime" label="发表时间"></el-table-column>
                        <el-table-column prop="category" label="著作类别"></el-table-column>
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
                    <el-dialog title="新增 学术著作" :visible.sync="dialogFormVisible" @close="doClear()">
                        <el-form :model="work" :rules="rules" ref="work">
                            <el-form-item v-if="disabledFormItems.workId" label="著作编号" :label-width="formLabelWidth"
                                prop="id">
                                <el-input v-model="work.id" autocomplete="off"
                                    :disabled="disabledFormItems.workId"></el-input>
                            </el-form-item>
                            <el-form-item label="著作名称" :label-width="formLabelWidth" prop="name">
                                <el-input v-model="work.name" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="出版社名称" :label-width="formLabelWidth" prop="press">
                                <el-input v-model="work.press" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="所有作者" :label-width="formLabelWidth" prop="authors">
                                <el-input v-model="work.authors" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="发表时间" :label-width="formLabelWidth" prop="publishTime">
                                <el-input v-model="work.publishTime" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="著作类别" :label-width="formLabelWidth" prop="category">
                                <el-select v-model="work.category" placeholder="请选择">
                                    <el-option label="专著" value="专著"></el-option>
                                    <el-option label="编著" value="编著"></el-option>
                                    <el-option label="译著" value="译著"></el-option>
                                    <el-option label="教材" value="教材"></el-option>
                                    <el-option label="其他" value="其他"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="所属单位" :label-width="formLabelWidth" prop="department">
                                <el-select v-model="work.department" placeholder="请选择">
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
            selectedIndex: '/works',
            tableData: [],
            rows: 10,
            total: 0,
            page: 1,
            // 是否打开弹窗
            dialogFormVisible: false,
            // 弹窗标题
            title: '新增页面',
            // 定义数组接收数据
            work:
                { id: '', name: '', press: '', authors: '', publishTime: '', department: '', category: '' },
            searchWork:
                { currentPage: 1, pageSize: 10, id: '', name: '', press: '', authors: '', publishTime: '', department: '', category: '' },
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
                workId: false,
            },
            asideClass: 'main-aside',
            filename: 'work_info',
            uploadUrl: 'http://localhost:8088/work/import', // 后端接收上传文件的URL
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
                workId: false
            };
            this.$refs.work.resetFields();
            this.work = this.$options.data.call(this).work;
        },
        // 编辑
        handleEdit(index, row) {
            this.dialogFormVisible = true;
            this.disabledFormItems = {
                workId: false
            }
            if (row) {
                this.title = '编辑 学术著作';
                this.work.id = row.id;
                this.work.name = row.name;
                this.work.press = row.press;
                this.work.authors = row.authors;
                this.work.publishTime = row.publishTime;
                this.work.department = row.department;
                this.work.category = row.category;
            }
        },
        // 删除
        async handleDelete(index, row) {
            console.log(index, row.id)
            let res = await http.post("/work/delete/" + row.id);
            if (res && res.code == 200) {
                this.$message.success("删除成功");
                this.fetch();
            }
        },
        // 提交
        doSubmit() {
            console.log("do submit");
            //表单验证
            this.$refs['work'].validate(async (valid) => {
                if (valid) {
                    //验证通过执行增加修改方法
                    console.log(this.work);

                    //获取后台请求书籍数据的地址
                    let res = await http.post("/work/upsert", this.work);
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
            let res = await http.get("/work/list");
            if (res && res.code == 200) {
                // console.log(res.data);
                this.tableData = res.data;
            }
        },
        async doSearch() {
            console.log("do search");
            let res = await http.get("/work/search", this.searchWork);
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
        },
        // 导入按钮的点击事件处理函数
        importExcel() {
            this.$refs.excelUpload.click(); // 触发文件选择对话框
        },
        // 文件上传前的钩子函数
        beforeUpload(file) {
            const isExcel = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheet' || file.type === 'application/vnd.ms-excel';
            if (!isExcel) {
                this.$message.error('请上传Excel文件');
            }
            return isExcel;
        },
        // 文件上传成功的回调函数
        handleSuccess(response) {
            if (response.code === 200) {
                this.$message.success('导入成功');
                this.fetch();
            } else {
                this.$message.error(response.message || '导入失败');
            }
        },
        // 文件上传失败的回调函数
        handleError() {
            this.$message.error('文件上传失败');
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

.el-upload {
  margin-top: 20px;
}
</style>