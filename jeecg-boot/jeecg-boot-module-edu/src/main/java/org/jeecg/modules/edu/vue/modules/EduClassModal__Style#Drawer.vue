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

        <a-form-item label="班级名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'className', validatorRules.className]" placeholder="请输入班级名称"></a-input>
        </a-form-item>
        <a-form-item label="学生人数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'studentNum', validatorRules.studentNum]" placeholder="请输入学生人数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="班主任" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'classTeacherId', validatorRules.classTeacherId]" placeholder="请输入班主任"></a-input>
        </a-form-item>
        <a-form-item label="年级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['classGrade', validatorRules.classGrade]" :trigger-change="true" dictCode="classGrade" placeholder="请选择年级"/>
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
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "EduClassModal",
    components: { 
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
          className: {rules: [
            {required: true, message: '请输入班级名称!'},
          ]},
          studentNum: {rules: [
            {required: true, message: '请输入学生人数!'},
            {pattern:/^-?\d+$/, message: '请输入整数!'},
          ]},
          classTeacherId: {rules: [
            {required: true, message: '请输入班主任!'},
          ]},
          classGrade: {rules: [
            {required: true, message: '请输入年级!'},
          ]},
        },
        url: {
          add: "/edu/eduClass/add",
          edit: "/edu/eduClass/edit",
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
          this.form.setFieldsValue(pick(this.model,'className','studentNum','classTeacherId','classGrade'))
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
        this.form.setFieldsValue(pick(row,'className','studentNum','classTeacherId','classGrade'))
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