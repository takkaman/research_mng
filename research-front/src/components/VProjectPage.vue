<template>
    <el-container class="main-container">
        <el-aside v-bind:class="asideClass">
            <LeftNav :activeIndex="selectedIndex"></LeftNav>
        </el-aside>
        <el-container>
            <el-header class="main-header">
                <TopNav></TopNav>
            </el-header>
            <el-main v-if="userId" class="main-center">
                <div class="Book" style="padding: 30px;">
                    <!-- 输入框搜索 -->
                    <el-form :model="searchProject" :inline="true" class="demo-form-inline">
                        <el-form-item label="项目编号 : ">
                            <el-input v-model="searchProject.projectId" placeholder="项目编号"></el-input>
                        </el-form-item>
                        <el-form-item label="项目名称 : ">
                            <el-input v-model="searchProject.name" placeholder="项目名称"></el-input>
                        </el-form-item>
                        <el-form-item label="项目分类 : ">
                            <el-input v-model="searchProject.category" placeholder="项目分类"></el-input>
                        </el-form-item>
                        <el-form-item label="负责人 : ">
                            <el-input v-model="searchProject.responsible" placeholder="负责人"></el-input>
                        </el-form-item>
                        <el-form-item label="所属单位 : ">
                            <el-input v-model="searchProject.department" placeholder="所属单位"></el-input>
                        </el-form-item>
                        <el-form-item label="经费卡号 : ">
                            <el-input v-model="searchProject.cardNum" placeholder="经费卡号"></el-input>
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
                        <el-table-column prop="projectId" label="项目编号"></el-table-column>
                        <el-table-column prop="cardNum" label="经费卡号"></el-table-column>
                        <el-table-column prop="name" label="项目名称"></el-table-column>
                        <el-table-column prop="responsible" label="负责人"></el-table-column>
                        <el-table-column prop="category" label="项目分类"></el-table-column>
                        <el-table-column prop="budget" label="批准经费"></el-table-column>
                        <el-table-column prop="department" label="所属单位"></el-table-column>
                        <el-table-column prop="startTime" :formatter="formatDate" label="立项日期"></el-table-column>
                        <el-table-column prop="endTime" :formatter="formatDate" label="计划结项日期"></el-table-column>
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
                    <el-dialog title="新增 科研项目" :visible.sync="dialogFormVisible" @close="doClear()">
                        <el-form :model="project" :rules="rules" ref="project">
                            <el-form-item label="项目编号" :label-width="formLabelWidth" prop="projectId">
                                <el-input v-model="project.projectId" autocomplete="off"
                                    :disabled="disabledFormItems.projectId"></el-input>
                            </el-form-item>
                            <el-form-item label="经费卡号" :label-width="formLabelWidth" prop="cardNum">
                                <el-input v-model="project.cardNum" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="项目名称" :label-width="formLabelWidth" prop="name">
                                <el-input v-model="project.name" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="负责人" :label-width="formLabelWidth" prop="responsible">
                                <el-input v-model="project.responsible" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="项目分类" :label-width="formLabelWidth" prop="category">
                                <el-select v-model="project.category" placeholder="请选择">
                                    <el-option label="省级" value="省级"></el-option>
                                    <el-option label="部级" value="部级"></el-option>
                                    <el-option label="国家级" value="国家级"></el-option>
                                    <el-option label="其他" value="其他"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="批准经费" :label-width="formLabelWidth" prop="budget">
                                <el-input v-model="project.budget" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="所属单位" :label-width="formLabelWidth" prop="department">
                                <el-select v-model="project.department" placeholder="请选择">
                                    <el-option label="英语系" value="英语系"></el-option>
                                    <el-option label="日语系" value="英语系"></el-option>
                                    <el-option label="法语系" value="法语系"></el-option>
                                    <el-option label="欧语系" value="欧语系"></el-option>
                                    <el-option label="外语教学部" value="外语教学部"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="立项日期" :label-width="formLabelWidth" prop="startTime">
                                <el-input v-model="project.startTime" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="计划结项日期" :label-width="formLabelWidth" prop="endTime">
                                <el-input v-model="project.endTime" autocomplete="off"></el-input>
                            </el-form-item>
                            <!-- <el-form-item label="书籍类型" :label-width="formLabelWidth" prop="booktype">
                                <el-select v-model="book.booktype" placeholder="请选择活动区域">
                                    <el-option v-for="t in types" :label="t.name" :value="t.name"
                                        :key="'key_' + t.id"></el-option>
                                </el-select>
                            </el-form-item> -->
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
            selectedIndex: '/vprojects',
            tableData: [],
            rows: 10,
            total: 0,
            page: 1,
            // 是否打开弹窗
            dialogFormVisible: false,
            // 弹窗标题
            title: '新增页面',
            // 定义数组接收数据
            project:
                { projectId: '', cardNum: '', name: '', responsible: '', category: '', budget: '', department: '', startTime: '', endTime: '' },
            searchProject:
                { currentPage: 1, pageSize: 10, projectId: '', cardNum: '', name: '', responsible: '', category: '', budget: '', department: '', startTime: '', endTime: '' },
            // 类型
            types: [],
            // 输入框长度
            formLabelWidth: '100px',
            //表单验证
            rules: {
                //定义验证格式
                projectId: [
                    { required: true, message: '请输入项目编号', trigger: 'blur' },
                    // { min: 1, max: 10, message: '长度在 1 到 10 个数字', trigger: 'blur' }
                ],
                name: [
                    { required: true, message: '请输入项目名称', trigger: 'change' },
                    { min: 10, max: 20, message: '长度在 10 到 20 个字符', trigger: 'blur' }
                ],
                category: [
                    { required: true, message: '请输入项目分类', trigger: 'blur' }
                ]
            },
            disabledFormItems: {
                projectId: false,
            },
            asideClass: 'main-aside',
            filename: 'vertical_project_info'
        }
    },
    created() {
        // this.$root.Bus.$on('aaa', v => {
        //     this.asideClass = v ? 'main-aside-collapsed' : 'main-aside';
        // });
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
                projectId: false
            };
            this.$refs.project.resetFields();
            this.project = this.$options.data.call(this).project;
        },
        // 编辑
        handleEdit(index, row) {
            this.dialogFormVisible = true;
            this.disabledFormItems = {
                projectId: true
            }
            if (row) {
                this.title = '编辑 科研项目';
                this.project.projectId = row.projectId;
                this.project.cardNum = row.cardNum;
                this.project.name = row.name;
                this.project.responsible = row.responsible;
                this.project.category = row.category;
                this.project.budget = row.budget;
                this.project.department = row.department;
                this.project.department = row.department;
                this.project.startTime = row.startTime;
                this.project.endTime = row.endTime;
            }
        },
        // 删除
        async handleDelete(index, row) {
            console.log(index, row.projectId)
            let res = await http.post("/vproject/delete/" + row.projectId);
            if (res && res.code == 200) {
                this.$message.success("删除成功");
                this.fetch();
            }
        },
        // 提交
        doSubmit() {
            console.log("do submit");
            //表单验证
            this.$refs['project'].validate(async (valid) => {
                if (valid) {
                    //验证通过执行增加修改方法
                    console.log(this.project);

                    //获取后台请求书籍数据的地址
                    let res = await http.post("/vproject/upsert", this.project);
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
            let res = await http.get("/vproject/list");
            if (res && res.code == 200) {
                // console.log(res.data);
                this.tableData = res.data;
            }
        },
        async doSearch() {
            console.log("do search");
            let res = await http.get("/vproject/search", this.searchProject);
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