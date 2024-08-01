<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">

            </div>
            <el-row>
                <el-col :span="24">
                    <div ref="barChart" style="width: 100%; height: 500px;"></div>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <div ref="barChart1" style="width: 100%; height: 500px;"></div>
                </el-col>
            </el-row>
        </el-card>
    </div>
</template>

<script>
import * as echarts from 'echarts';
import http from "@/utils/http";

export default {
    name: 'BarChart',
    data() {
        return {
            chart: null,
            yearStatsData: [], // 存储从后端获取的数据
            categoryStatsData: [], // 存储从后端获取的数据
            colors: ['#ff4757', '#7bed9f', '#70a1ff', '#5352ed', '#f6933d']
        };
    },
    mounted() {
        this.fetchData();
    },
    methods: {
        async fetchData() {
            // 模拟从后端获取数据
            await http.get('/stats/all').then(response => {
                this.yearStatsData = response.data; // 假设后端返回的数据格式正确
            });

            await http.get('/stats/category').then(response => {
                this.categoryStatsData = response.data; // 假设后端返回的数据格式正确
            });

            console.log(this.categoryStatsData)

            this.yearData = this.yearStatsData;
            this.categoryData = this.categoryStatsData;
            this.initChart();
            this.initChart1();
        },
        initChart() {
            const years = this.yearData.map(item => item.year);
            const productNames = [
                ...new Set(this.yearData.flatMap(data => data.products.map(product => product.name)))
            ]; // 假设所有年份的产品相同
            console.log(productNames)

            // 创建一个完整的数据集，包括所有产品和所有年份的组合
            const fullDataSet = productNames.flatMap((productName) => {
                return years.map(year => {
                    const productData = this.yearData.find(data => data.year === year && data.products.some(p => p.name === productName));
                    return {
                        year: year,
                        productName: productName,
                        value: productData ? productData.products.find(p => p.name === productName).value : 0 // 如果没有找到，则默认为0
                    };
                });
            });

            // 然后，根据完整的数据集构建 series
            const series = productNames.map((name, index) => {
                const data = fullDataSet.filter(item => item.productName === name).map(item => item.value);
                return {
                    name: name,
                    type: 'bar',
                    data: data,
                    stack: index.toString() // 将相同索引的柱子堆叠在一起
                };
            });

            this.chart = echarts.init(this.$refs.barChart1);
            const option = {
                title: { text: '学院近5年成果统计' },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    data: productNames
                },
                xAxis: {
                    type: 'category',
                    data: years
                },
                yAxis: {
                    type: 'value'
                },
                series: series
            };

            this.chart.setOption(option);
        },
        initChart1() {
            const names = this.categoryData.map(item => item.name);
            this.chart = echarts.init(this.$refs.barChart);
            const option = {
                title: {
                    text: new Date().getFullYear() + '年学院项目立项统计',
                    left: 'left'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    data: names
                },
                xAxis: {
                    type: 'category',
                    data: this.categoryData.map(item => item.name)
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: this.categoryData.map((item, index) => ({
                        value: item.value,
                        itemStyle: {
                            color: this.colors[index % this.colors.length] // 应用颜色
                        }
                    })),
                    type: 'bar',
                    label: {
                        show: false,
                        position: 'top'
                    }
                }]
            };
            this.chart.setOption(option);
        }
    }
};
</script>

<style>
.box-card {
    margin: 30px;
}
</style>