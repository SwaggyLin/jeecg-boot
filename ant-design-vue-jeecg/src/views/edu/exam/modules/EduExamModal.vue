<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="考试名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'examName', validatorRules.examName]" placeholder="请输入考试名称"></a-input>
        </a-form-item>
        <a-form-item label="学期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['examPeriod', validatorRules.examPeriod]" :trigger-change="true" dictCode="exam_period" placeholder="请选择学期"/>
        </a-form-item>
        <a-form-item label="科目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            style="width: 100%"
            placeholder="请选择科目"
            optionFilterProp = "children"
            v-decorator="['subjectId', validatorRules.subjectId]">
            <a-select-option v-for="(subject,subjectindex) in subjectList" :key="subjectindex.toString()" :value="subject.id">
              {{ subject.subjectName }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="考试类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['examType', validatorRules.examType]" :trigger-change="true" dictCode="exam_type" placeholder="请选择考试类型"/>
        </a-form-item>
        <a-form-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择开始时间" v-decorator="[ 'startTime', validatorRules.startTime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择结束时间" v-decorator="[ 'endTime', validatorRules.endTime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="考试班级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            mode="multiple"
            style="width: 100%"
            placeholder="请选择考试班级"
            optionFilterProp = "children"
            v-decorator="['selectedClass', validatorRules.selectedClass]"
            >
            <a-select-option v-for="(eduClass,eduClassindex) in eduClassList" :key="eduClassindex.toString()" :value="eduClass.id">
              {{ eduClass.className }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="考试状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['examState', validatorRules.examState]" :trigger-change="true" dictCode="exam_state" placeholder="请选择考试状态"/>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk" :disabled="isDisabled">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import {queryAllSubjects,queryAllClasses,queryExamClasses} from "@/api/api"

  
  export default {
    name: "EduExamModal",
    components: { 
      JDate,
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        subjectList:[],
        eduClassList:[],
        selectedClass:[],
        isDisabled:false,
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          examName: {rules: [
            {required: true, message: '请输入考试名称!'},
          ]},
          examType: {rules: [
            {required: true, message: '请输入考试类型!'},
          ]},
          startTime: {rules: [
            {required: true, message: '请输入开始时间!'},
          ]},
          endTime: {rules: [
            {required: true, message: '请输入结束时间!'},
          ]},
          examState: {rules: [
            {required: true, message: '请输入考试状态!'},
          ]},
          examPeriod: {rules: [
            {required: true, message: '请输入学期!'},
          ]},
          subjectId: {rules: [
            {required: true, message: '请输入科目!'},
          ]},
          selectedClass: {rules: [
            {required: true, message: '请输入考试班级!'},
          ]},
        },
        url: {
          add: "/edu/exam/eduExam/add",
          edit: "/edu/exam/eduExam/edit",
        }
      }
    },
    created () {
    },
    methods: {
      initialSubjectList(){
        queryAllSubjects().then((res)=>{
          if(res.success){
            this.subjectList = res.result;
          }else{
            console.log(res.message);
          }
        });
      },
      initialClassList(){
        queryAllClasses().then((res)=>{
          if(res.success){
            this.eduClassList = res.result;
          }else{
            console.log(res.message);
          }
        });
      },
      loadExamClasses(examId){
        queryExamClasses({examId:examId}).then((res)=>{
          if(res.success){
            //this.selectedClass = res.result;
            this.form.setFieldsValue({
              selectedClass:res.result
            });
          }else{
            console.log(res.message);
          }
        });
      },
      add () {
        this.edit({});
      },
      edit (record) {
        this.initialSubjectList();
        this.initialClassList();
        if(record.hasOwnProperty("id")){
          this.loadExamClasses(record.id);
          //判断考试状态
          if(record.examState!='1'){//考试中
            this.$message.warning("考试已开始或已结束,不可编辑!")
            this.isDisabled=true;
          }
        }else{
          this.isDisabled=false ;
        }
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'examName','examType','startTime','endTime','examState','examPeriod','subjectId'))
        })
      },
      close () {
        this.$emit('close');
        this.selectedClass = [];
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            formData.selectedClasses = formData.selectedClass.length>0?formData.selectedClass.join(","):'';
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'examName','examType','startTime','endTime','examState'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>