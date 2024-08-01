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
                    <el-form :model="searchAward" :inline="true" class="demo-form-inline">
                        <el-form-item label="获奖编号 : ">
                            <el-input v-model="searchAward.id" placeholder="获奖名称"></el-input>
                        </el-form-item>
                        <el-form-item label="获奖名称 : ">
                            <el-input v-model="searchAward.name" placeholder="获奖名称"></el-input>
                        </el-form-item>
                        <el-form-item label="所属单位 : ">
                            <el-input v-model="searchAward.department" placeholder="所属单位"></el-input>
                        </el-form-item>
                        <!-- <el-form-item label="获奖时间 : ">
                            <el-input v-model="searchAward.awardTime" placeholder="获奖时间"></el-input>
                        </el-form-item> -->
                        <el-form-item label="获奖级别 : ">
                            <el-input v-model="searchAward.awardScope" placeholder="获奖级别"></el-input>
                        </el-form-item>
                        <el-form-item label="获奖等级 : ">
                            <el-input v-model="searchAward.awardLevel" placeholder="获奖等级"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" icon="el-icon-search" plain @click="doSearch()">查询</el-button>
                            <el-button v-if="userType == 1" type="primary" icon="el-icon-circle-plus-outline" plain
                                @click="dialogFormVisible = true">新增</el-button>
                            <el-button type="primary" icon="el-icon-download" plain
                                @click="exportToExcel">导出</el-button>
                        </el-form-item>
                    </el-form>
                    <!-- 成果获奖表格 -->
                    <el-checkbox-group v-model="checkedColumns">
                        <el-checkbox v-for="column in tableColumns" :key="column.prop" :label="column.prop">
                            {{ column.label }}
                        </el-checkbox>
                    </el-checkbox-group>
                    <el-table :data="tableData" style="width: 100%">
                        <el-table-column prop="id" label="获奖编号"></el-table-column>
                        <el-table-column prop="name" label="获奖名称"></el-table-column>
                        <el-table-column prop="department" label="所属单位"></el-table-column>
                        <el-table-column prop="awardTime" label="获奖时间"></el-table-column>
                        <el-table-column prop="awardScope" label="获奖级别"></el-table-column>
                        <el-table-column prop="awardLevel" label="获奖等级"></el-table-column>
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
                    <el-dialog title="新增 成果获奖" :visible.sync="dialogFormVisible" @close="doClear()">
                        <el-form :model="award" :rules="rules" ref="award">
                            <el-form-item label="获奖编号" :label-width="formLabelWidth" prop="id">
                                <el-input v-model="award.id" autocomplete="off"
                                    :disabled="disabledFormItems.awardId"></el-input>
                            </el-form-item>
                            <el-form-item label="获奖名称" :label-width="formLabelWidth" prop="id">
                                <el-input v-model="award.name" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="所属单位" :label-width="formLabelWidth" prop="name">
                                <el-select v-model="award.department" placeholder="请选择">
                                    <el-option label="英语系" value="英语系"></el-option>
                                    <el-option label="日语系" value="英语系"></el-option>
                                    <el-option label="法语系" value="法语系"></el-option>
                                    <el-option label="欧语系" value="欧语系"></el-option>
                                    <el-option label="外语教学部" value="外语教学部"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="获奖时间" :label-width="formLabelWidth" prop="press">
                                <el-input v-model="award.awardTime" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="获奖级别" :label-width="formLabelWidth" prop="awardScope">
                                <el-select v-model="award.awardScope" placeholder="请选择">
                                    <el-option label="国家级" value="国家级"></el-option>
                                    <el-option label="部级" value="部级"></el-option>
                                    <el-option label="省级" value="省级"></el-option>
                                    <el-option label="地市级" value="地市级"></el-option>
                                    <el-option label="校级" value="校级"></el-option>
                                    <el-option label="其他" value="其他"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="获奖等级" :label-width="formLabelWidth" prop="awardLevel">
                                <el-select v-model="award.awardLevel" placeholder="请选择">
                                    <el-option label="特等奖" value="特等奖"></el-option>
                                    <el-option label="一等奖" value="一等奖"></el-option>
                                    <el-option label="二等奖" value="二等奖"></el-option>
                                    <el-option label="三等级" value="三等级"></el-option>
                                    <el-option label="其他" value="其他"></el-option>
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
            selectedIndex: '/awards',
            tableData: [],
            rows: 10,
            total: 0,
            page: 1,
            // 是否打开弹窗
            dialogFormVisible: false,
            // 弹窗标题
            title: '新增页面',
            // 定义数组接收数据
            award:
                { id: '', name: '', department: '', awardTime: '', awardScope: '', awardLevel: '' },
            searchAward:
                { currentPage: 1, pageSize: 10, id: '', name: '', department: '', awardTime: '', awardScope: '', awardLevel: '' },
            // 类型
            types: [],
            // 输入框长度
            formLabelWidth: '100px',
            //表单验证
            rules: {
                //定义验证格式
                id: [
                    { required: true, message: '请输入著作编号', trigger: 'blur' },
                    // { min: 1, max: 10, message: '长度在 1 到 10 个数字', trigger: 'blur' }
                ],
                name: [
                    { required: true, message: '请输入著作名称', trigger: 'change' },
                    { min: 5, max: 20, message: '长度在 10 到 30 个字符', trigger: 'blur' }
                ]
            },
            disabledFormItems: {
                awardId: false,
            },
            asideClass: 'main-aside',
            filename: 'award_info',
            tableColumns: [
                { prop: "id", label: "获奖编号" },
                { prop: "name", label: "获奖名称" },
                { prop: "department", label: "所属单位" },
                { prop: "awardTime", label: "获奖时间", formatter: this.formatDate },
                { prop: "awardScope", label: "获奖级别" },
                { prop: "awardLevel", label: "获奖等级" },
                // 更多列配置...
            ],
            checkedColumns: ['id', 'name', 'department', 'awardTime', 'awardScope', 'awardLevel'],
        }
    },
    created() {
        this.fetch();
    },
    monted() {
        this.selectedIndex = '/awards'
    },
    methods: {
        formatDate(row, column, cellValue) {
            // 使用moment.js来格式化日期
            return moment(cellValue).format('YYYY-MM-DD');
        },
        // 初始化方法
        doClear() {
            // console.log("clear triggerred");
            this.dialogFormVisible = false;
            this.disabledFormItems = {
                awardId: false
            };
            this.$refs.award.resetFields();
            this.award = this.$options.data.call(this).award;
        },
        // 编辑
        handleEdit(index, row) {
            this.dialogFormVisible = true;
            this.disabledFormItems = {
                awardId: true
            }
            if (row) {
                this.title = '编辑 学术著作';
                this.award.id = row.id;
                this.award.name = row.name;
                this.award.department = row.department;
                this.award.awardTime = row.awardTime;
                this.award.awardScope = row.awardScope;
                this.award.awardLevel = row.awardLevel;
            }
        },
        // 删除
        handleDelete(index, row) {
            this.$confirm("确定删除吗?", "提示", {
                confirmButtonText: "确定",         // 确认按钮文本
                cancelButtonText: "取消",          // 取消按钮文本
                type: "warning",                   // 提示类型（警告）
            }).then(async () => {
                let res = await http.post("/award/delete/" + row.id);
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
            this.$refs['award'].validate(async (valid) => {
                if (valid) {
                    //验证通过执行增加修改方法
                    // console.log(this.award);

                    //获取后台请求书籍数据的地址
                    let res = await http.post("/award/upsert", this.award);
                    // console.log(res);
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
            // console.log("do fetch");
            let res = await http.get("/award/list");
            if (res && res.code == 200) {
                // console.log(res.data);
                this.tableData = res.data;
            }
        },
        async doSearch() {
            // console.log("do search");
            let res = await http.get("/award/search", this.searchAward);
            if (res && res.code == 200) {
                // console.log(res.data);
                this.tableData = res.data.records;
            }
        },
        exportToExcel() {
            const tableData = this.tableData; // 这里应该是你的数据源
            // const headers = ['获奖编号', '获奖名称', '所属单位', '获奖时间', '获奖级别', '获奖等级'];
            // 创建一个映射表，将原始的英文属性名映射到中文表头
            const headerMapping = {
                id: '获奖编号',
                name: '获奖名称',
                department: '所属单位',
                awardTime: '获奖时间',
                awardScope: '获奖级别',
                awardLevel: '获奖等级'
            };

            const filteredData = tableData.map(row => {
                const filteredRow = {};
                this.checkedColumns.forEach(colProp => {
                    filteredRow[colProp] = row[colProp];
                });
                return filteredRow;
            });

            // console.log(filteredData);

            // 使用映射表重新组织数据
            const mappedData = filteredData.map(row => {
                const newRow = {};
                Object.keys(headerMapping).forEach(key => {
                    if (key in row) {
                        newRow[headerMapping[key]] = row[key];
                    }
                });
                return newRow;
            });

            const mappedHeaders = this.checkedColumns.map(colKey => headerMapping[colKey]);
            console.log(mappedHeaders);

            const worksheet = XLSX.utils.json_to_sheet(mappedData, { header: mappedHeaders, skipHeader: false });
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