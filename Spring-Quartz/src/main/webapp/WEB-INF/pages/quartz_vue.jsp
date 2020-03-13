<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String cssPath = request.getContextPath();
    String cssBasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+cssPath+"/";
%>
<!DOCTYPE html>
<!--在html中添加xmlns:th="http://www.thymeleaf.org" ,可避免编辑器出现html验证错误，这不是必须的-->
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="<%=cssPath%>/resources/vue/vue.js"></script>
    <script src="http://cdn.bootcss.com/vue-resource/1.3.4/vue-resource.js"></script>

    <script type="text/javascript" src="<%=cssPath%>/resources/element-ui/lib/index.js"></script>
    <link rel="stylesheet" href="<%=cssPath%>/resources/element-ui/lib/theme-chalk/index.css">

    <style>
        #top {
            background:#20A0FF;
            padding:5px;
            overflow:hidden
        }
    </style>
</head>
<body>
<div id="app">
    <div id="top">
        <el-button type="text" @click="search" style="color:white">查询</el-button>
        <el-button type="text" @click="handleadd" style="color:white">添加</el-button>
        </span>
    </div>

    <br/>

    <div style="margin-top:15px">

        <el-table ref="testTable" :data="tableData" style="width:100%" border stripe fit>
            <el-table-column prop="jobName" label="任务名称" sortable show-overflow-tooltip width="105"></el-table-column>
            <el-table-column prop="jobStatus" label="任务状态" width="100" :formatter="formatStatus" header-align="center"></el-table-column>
            <el-table-column prop="jobGroup" label="任务所在组" width="130"></el-table-column>
            <el-table-column prop="jobClassName" label="任务类名" width="193"></el-table-column>
            <el-table-column prop="triggerName" label="触发器名称" width="100"></el-table-column>
            <el-table-column prop="triggerGroup" label="触发器所在组" width="120"></el-table-column>
            <el-table-column prop="cronExpression" label="表达式" width="195"></el-table-column>
<!--            <el-table-column prop="jobDescription" label="任务描述" width="160"></el-table-column>-->
            <el-table-column label="操作" width="320">
                <template slot-scope="scope">
                    <el-button size="small" type="warning" @click="handlePause(scope.$index, scope.row)">暂停</el-button>
                    <el-button size="small" type="info" @click="handleResume(scope.$index, scope.row)">恢复</el-button>
                    <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    <el-button size="small" type="success" @click="handleUpdate(scope.$index, scope.row)">修改</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div align="center">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[10, 20, 30, 40]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="totalCount">
            </el-pagination>
        </div>
    </div>

    <el-dialog title="添加任务" :visible.syn="dialogFormVisible">
        <el-form :model="form">
            <el-form-item label="任务名称" label-width="120px" style="width:65%">
                <el-input v-model.trim="form.jobName" auto-complete="off" placeholder="请输入任务名称"></el-input>
            </el-form-item>
            <el-form-item label="任务分组" label-width="120px" style="width:65%">
                <el-input v-model.trim="form.jobGroup" auto-complete="off" placeholder="请输入任务分组"></el-input>
            </el-form-item>
            <el-form-item label="触发器名称" label-width="120px" style="width:65%">
                <el-input v-model.trim="form.triggerName" auto-complete="off" placeholder="请输入触发器名称"></el-input>
            </el-form-item>
            <el-form-item label="触发器分组" label-width="120px" style="width:65%">
                <el-input v-model.trim="form.triggerGroup" auto-complete="off" placeholder="请输入触发器分组"></el-input>
            </el-form-item>
            <el-form-item label="表达式" label-width="120px" style="width:65%">
                <el-input v-model.trim="form.cronExpression" auto-complete="off" placeholder="请输入时间表达式"></el-input>
            </el-form-item>
            <el-form-item label="任务类名全路径" label-width="120px" style="width:65%">
                <el-input v-model.trim="form.jobClassName" auto-complete="off" placeholder="任务类名全路径"></el-input>
            </el-form-item>
            <el-form-item label="任务描述" label-width="120px" style="width:65%">
                <el-input v-model.trim="form.jobDescription" auto-complete="off" placeholder="请输入任务描述"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="add">确 定</el-button>
        </div>
    </el-dialog>

    <el-dialog title="修改任务" :visible.syn="updateFormVisible">
        <el-form :model="updateform">
            <el-form-item label="表达式" label-width="120px" style="width:75%">
                <el-input v-model.trim="updateform.cronExpression" auto-complete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="updateFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="update">确 定</el-button>
        </div>
    </el-dialog>

</div>
</div>
</body>
<script type="text/javascript">

    var vue = new Vue({
        el:'#app',
        data: {
            //表格当前页数据
            tableData: [],
            //请求的URL
            url:'/job/queryJob',
            //默认每页数据量
            pageSize: 10,
            //当前页码
            currentPage: 1,
            //查询的页码
            start: 1,
            //默认数据总数
            totalCount: 200,
            //添加对话框默认可见性
            dialogFormVisible: false,
            //修改对话框默认可见性
            updateFormVisible: false,
            //提交的表单
            form: {
                jobName: '',
                jobGroup: '',
                triggerName: '',
                triggerGroup: '',
                jobClassName: '',
                cronExpression: '',
                jobDescription: ''
            },
            updateform: {
                id : '',
                jobName: '',
                jobGroup: '',
                triggerName: '',
                triggerGroup: '',
                jobClassName: '',
                cronExpression: '',
                jobDescription: ''
            },
        },
        created(){
           this.loadData(this.currentPage,this.pageSize);
        },
        methods: {
            //从服务器读取数据
            loadData: function(pageNum, pageSize){
                this.$http.get('/job/queryJob?' + 'pageNum=' +  pageNum + '&pageSize=' + pageSize).then(function(res){
                    console.log(res);
                    if (res.body){
                        let data = res.body && res.body.data;
                        this.tableData = data && data.records;
                        this.totalCount = data && data.total;
                    }else{
                        console.log('failed');
                    }
                },function(){
                    console.log('failed');
                });
            },

            //单行删除
            handleDelete: function(index, row) {
                this.$http.post('/job/deleteJob',{"jobName":row.jobName,"jobGroup":row.jobGroup,"id":row.id,
                        "triggerName":row.triggerName,"triggerGroup":row.triggerGroup},
                    {emulateJSON: true}).then(function(res){
                    this.loadData( this.currentPage, this.pageSize);
                },function(){
                    console.log('failed');
                });
            },

            //暂停任务
            handlePause: function(index, row){
                this.$http.post('/job/pauseJob',{"jobName":row.jobName,"jobGroup":row.jobGroup,"id":row.id,
                        "triggerName":row.triggerName,"triggerGroup":row.triggerGroup}
                ,{emulateJSON: true}).then(function(res){
                    this.loadData( this.currentPage, this.pageSize);
                },function(){
                    console.log('failed');
                });
            },

            //恢复任务
            handleResume: function(index, row){
                this.$http.post('/job/resumeJob',{"jobName":row.jobName,"jobGroup":row.jobGroup,"id":row.id,
                        "triggerName":row.triggerName,"triggerGroup":row.triggerGroup},
                    {emulateJSON: true}).then(function(res){
                    this.loadData( this.currentPage, this.pageSize);
                },function(){
                    console.log('failed');
                });
            },

            //搜索
            search: function(){
                this.loadData(this.currentPage, this.pageSize);
            },

            //弹出对话框
            handleadd: function(){
                this.dialogFormVisible = true;
            },

            //添加
            add: function(){
                this.$http.post('/job/addJob',
                    {"jobName":this.form.jobName,"jobGroup":this.form.jobGroup,"jobClassName":this.form.jobClassName,
                        "triggerName":this.form.triggerName,"triggerGroup":this.form.triggerGroup,
                        "cronExpression":this.form.cronExpression,"jobDescription":this.form.jobDescription},
                    {emulateJSON: true}).then(function(res){
                    this.loadData(this.currentPage, this.pageSize);
                    this.dialogFormVisible = false;
                },function(){
                    console.log('failed');
                });
            },

            //更新
            handleUpdate: function(index, row){
                this.updateform.cronExpression = '';
                console.log(row);
                this.updateFormVisible = true;
                this.updateform.jobName = row.jobName;
                this.updateform.jobGroup = row.jobGroup;
                this.updateform.id=row.id;
                this.updateform.triggerName= row.triggerName;
                this.updateform.triggerGroup= row.triggerGroup;
            },

            //更新任务
            update: function(){
                this.$http.post
                ('/job/rescheduleJob',
                    {"jobName":this.updateform.jobName,"jobGroup":this.updateform.jobGroup,
                        "triggerName":this.updateform.triggerName,"triggerGroup":this.updateform.triggerGroup,
                        "cronExpression":this.updateform.cronExpression,"id": this.updateform.id
                    },{emulateJSON: true}
                ).then(function(res){
                    this.loadData(this.currentPage, this.pageSize);
                    this.updateFormVisible = false;
                },function(){
                    console.log('failed');
                });

            },
            //每页显示数据量变更
            handleSizeChange: function(val) {
                this.pageSize = val;
                this.loadData(this.currentPage, this.pageSize);
            },
            //页码变更
            handleCurrentChange: function(val) {
                this.currentPage = val;
                this.loadData(this.currentPage, this.pageSize);
            },
            formatStatus(row, column, cellValue, index){
                if (cellValue === "1"){
                    return "运行中";
                }else if (cellValue === "0"){
                    return "暂停";
                }
            }
        }
    });
</script>
</html>