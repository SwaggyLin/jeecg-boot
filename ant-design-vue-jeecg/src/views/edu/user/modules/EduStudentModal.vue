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

        <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'studentName', validatorRules.studentName]" placeholder="请输入姓名"></a-input>
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['sex', validatorRules.sex]" :trigger-change="true" dictCode="sex" placeholder="请选择性别"/>
        </a-form-item>
        <a-form-item label="出生年月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-month-picker placeholder="请选择出生年月" v-decorator="[ 'birthDate', validatorRules.birthDate]" :trigger-change="true" style="width: 100%" @change="this.handleDateChange"/>
        </a-form-item>
        <a-form-item label="家庭住址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'address', validatorRules.address]" placeholder="请输入家庭住址"></a-input>
        </a-form-item>
        <a-form-item label="家长姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'parentName', validatorRules.parentName]" placeholder="请输入家长姓名"></a-input>
        </a-form-item>
        <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'mobile', validatorRules.mobile]" placeholder="请输入联系电话"></a-input>
        </a-form-item>
        <a-form-item label="座号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'seatNum', validatorRules.seatNum]" placeholder="请输入座号"></a-input>
        </a-form-item>
        <a-form-item label="年龄" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'age', validatorRules.age]" placeholder="请输入年龄" disabled style="width: 100%"/>
        </a-form-item>
        <a-form-item label="所属班级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            v-decorator="[ 'classId', validatorRules.classId]"
            style="width: 100%"
            placeholder="请选择班级"
            optionFilterProp = "children"
            :showSearch="true">
            <a-select-option v-for="(eduClass) in eduClassList" :key="eduClass.id" :value="eduClass.id">
              {{ eduClass.className }}
            </a-select-option>
          </a-select>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'  
  import {queryAllClasses} from '@/api/api'
  import moment from 'moment'
  
  export default {
    name: "EduStudentModal",
    components: { 
      JDate
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        eduClassList:[],
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
          studentName: {rules: [
            {required: true, message: '请输入姓名!'},
          ]},
          sex: {rules: [
            {required: true, message: '请输入性别!'},
          ]},
          birthDate: {rules: [
            {required: true, message: '请输入出生年月!'},
          ]},
          address: {rules: [
            {required: true, message: '请输入家庭住址!'},
          ]},
          parentName: {rules: [
            {required: true, message: '请输入家长姓名!'},
          ]},
          mobile: {rules: [
            {required: true, message: '请输入联系电话!'},
            {pattern:/^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
          ]},
          seatNum: {rules: [
            {required: true, message: '请输入座号!'},
          ]},
          age: {rules: [
            {required: true, message: '请输入年龄!'},
           {pattern:/^-?\d+\.?\d*$/, message: '请输入数字!'},
          ]},
          classId: {rules: [
            {required: true, message: '请选择班级!'},
          ]},
        },
        url: {
          add: "/edu/user/eduStudent/add",
          edit: "/edu/user/eduStudent/edit",
        }
      }
    },
    created () {
    },
    methods: {
      handleDateChange:function(value, dateString){
        console.log(value, dateString);
        if(value!=null&&dateString!=""){
          let birthday=new Date(dateString.replace(/-/g, "/"));
          let nowDate = new Date();
          let age = nowDate.getFullYear() - birthday.getFullYear() - (nowDate.getMonth() < birthday.getMonth() || (birthday.getMonth() == birthday.getMonth())? 1 : 0);
          age=age>0?age:0;
          this.form.setFieldsValue({
            age: age
          });
        }else{
          this.form.setFieldsValue({
            age: null
          });
        }
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
      add () {
        this.edit({});
      },
      edit (record) {
        this.initialClassList();
        this.form.resetFields();
        record.birthDate=moment(record.birthDate, 'YYYY-MM');
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'studentName','sex','birthDate','address','parentName','mobile','seatNum','age','classId'))
        })
      },
      close () {
        this.$emit('close');
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
        this.form.setFieldsValue(pick(row,'studentName','sex','birthDate','address','parentName','mobile','seatNum','age','classId'))
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