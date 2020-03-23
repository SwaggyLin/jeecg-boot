<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'studentName', validatorRules.studentName]" placeholder="请输入姓名"></a-input>
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['sex', validatorRules.sex]" :trigger-change="true" dictCode="sex" placeholder="请选择性别"/>
        </a-form-item>
        <a-form-item label="出生年月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择出生年月" v-decorator="[ 'birthDate', validatorRules.birthDate]" :trigger-change="true" style="width: 100%"/>
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
          <a-input-number v-decorator="[ 'age', validatorRules.age]" placeholder="请输入年龄" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="所属班级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['classId', validatorRules.classId]" :trigger-change="true" dictCode="" placeholder="请选择所属班级"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: "EduStudentModal",
    components: { 
      JDate,
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
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
          ]},
        },
        url: {
          add: "/edu/eduStudent/add",
          edit: "/edu/eduStudent/edit",
        }
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
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
      },

      
    }
  }
</script>