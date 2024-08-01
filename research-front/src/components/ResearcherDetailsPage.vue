<template>
    <el-container class="main-container">
        <el-aside v-bind:class="asideClass">
            <LeftNav></LeftNav>
        </el-aside>
        <el-container>
            <el-header class="main-header">
                <TopNav></TopNav>
            </el-header>
            <el-main class="main-center">

                <div class="Book" style="padding: 30px;">
                    <h2>科研详情</h2>
                    <hr style="margin-top: 20px; margin-bottom: 10px;">
                    <!-- 输入框搜索 -->
                    <el-form :model="searchResearcher" :inline="true" class="demo-form-inline">
                        <el-form-item label="开始时间 : ">
                            <el-date-picker v-model="searchResearcher.startTime" type="date" placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="结束时间 : ">
                            <el-date-picker v-model="searchResearcher.endTime" type="date" placeholder="选择日期">
                            </el-date-picker> </el-form-item>
                        <el-form-item>
                            <el-button type="primary" icon="el-icon-search" plain @click="doSearch()">查询</el-button>
                        </el-form-item>
                    </el-form>
                    <!-- 基本信息 -->
                    <h4 style="font-weight: bold; color: #A4A6A8;">[基本信息]</h4>
                    <el-table :data="tableData" style="width: 100%; margin-bottom: 20px;" :cell-class-name="addClass"
                        :show-header="false">
                        <el-table-column prop="a1"></el-table-column>
                        <el-table-column prop="a2"></el-table-column>
                        <el-table-column prop="a3"></el-table-column>
                        <el-table-column prop="a4"></el-table-column>
                    </el-table>
                    <!-- 纵向科研项目表格 -->
                    <h4 style="font-weight: bold; color: #A4A6A8;">[纵向项目]</h4>
                    <el-table :data="vProjectTableData" style="width: 100%; margin-bottom: 20px;"
                        :header-cell-style="{ 'background-color': '#F0F5F9' }">
                        <el-table-column prop="projectId" label="项目编号"></el-table-column>
                        <el-table-column prop="cardNum" label="经费卡号"></el-table-column>
                        <el-table-column prop="name" label="项目名称"></el-table-column>
                        <el-table-column prop="responsible" label="负责人"></el-table-column>
                        <el-table-column prop="category" label="项目分类"></el-table-column>
                        <el-table-column prop="budget" label="批准经费"></el-table-column>
                        <el-table-column prop="department" label="所属单位"></el-table-column>
                        <el-table-column prop="startTime" :formatter="formatDate" label="立项日期"></el-table-column>
                        <el-table-column prop="endTime" :formatter="formatDate" label="计划结项日期"></el-table-column>
                    </el-table>
                    <!-- 著作表格 -->
                    <h4 style="font-weight: bold; color: #A4A6A8;">[著作]</h4>
                    <el-table :data="workTableData" style="width: 100%; margin-bottom: 20px;"
                        :header-cell-style="{ 'background-color': '#F0F5F9' }">
                        <el-table-column prop="id" label="著作编号"></el-table-column>
                        <el-table-column prop="name" label="著作名称"></el-table-column>
                        <el-table-column prop="press" label="出版社名称"></el-table-column>
                        <el-table-column prop="authors" label="所有作者"></el-table-column>
                        <el-table-column prop="publishTime" label="发表时间"></el-table-column>
                        <el-table-column prop="department" label="所属单位"></el-table-column>
                    </el-table>
                    <!-- 成果获奖表格 -->
                    <!-- <el-table :data="awardTableData" style="width: 100%">
                        <el-table-column prop="id" label="获奖编号"></el-table-column>
                        <el-table-column prop="name" label="获奖名称"></el-table-column>
                        <el-table-column prop="department" label="所属单位"></el-table-column>
                        <el-table-column prop="awardTime" label="获奖时间"></el-table-column>
                        <el-table-column prop="awardScope" label="获奖级别"></el-table-column>
                        <el-table-column prop="awardLevel" label="获奖等级"></el-table-column>
                    </el-table> -->
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

// 导出模块
export default {
    components: {
        TopNav, LeftNav
    },
    data() {
        return {
            tableData: [
                { a1: "姓名", a2: "张三", a3: "单位", a4: "复旦大学" },
                { a1: "最高学位", a2: "博士", a3: "职级", a4: "副教授" }
            ],
            vProjectTableData: [],
            workTableData: [],
            awardTableData: [],
            rows: 10,
            total: 0,
            page: 1,
            researcherId: this.$route.params.researcherId,
            // 是否打开弹窗
            dialogFormVisible: false,
            // 弹窗标题
            title: '新增页面',
            // 定义数组接收数据
            researcher:
                { id: '', name: '', title: '', subject: '', degree: '', department: '' },
            searchResearcher:
                { startTime: '', endTime: '' },
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
            asideClass: 'main-aside'
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
        addClass({ rowIndex, columnIndex }) {
            console.log(rowIndex)
            if (columnIndex == 0 || columnIndex == 2) {
                return "custom-column"
            }
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
            this.$router.push({ path: `/researcher/details/${id}` });
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
            console.log("do fetch" + this.researcherId);

            let res = await http.get("/researcher/details/" + this.researcherId);
            if (res && res.code == 200) {
                console.log(res.data);
                this.researcher = res.data.researcher;
                this.tableData = [
                    { a1: "姓名", a2: this.researcher.name, a3: "单位", a4: this.researcher.department },
                    { a1: "最高学位", a2: this.researcher.degree, a3: "职级", a4: this.researcher.title }
                ];

                this.vProjectTableData = res.data.projects;
                this.workTableData = res.data.works;
            }
        },
        async doSearch() {
            console.log("do search" + this.searchResearcher.startTime + " " + this.searchResearcher.endTime);
            let res = await http.get("/researcher/details/" + this.researcherId + "/search", { "startTime": this.searchResearcher.startTime, "endTime": this.searchResearcher.endTime });
            if (res && res.code == 200) {
                console.log(res.data);
                this.vProjectTableData = res.data.projects;
                this.workTableData = res.data.works;
            }
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
<style>
.custom-column {
    /* 自定义样式 */
    background-color: #F0F5F9;
    font-weight: bold;
    color: #939597;
}

.first-row {
    display: none;
}
</style>