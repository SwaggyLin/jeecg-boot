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

        <a-form-item label="教师名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'teacherName', validatorRules.teacherName]" placeholder="请输入教师名称"></a-input>
        </a-form-item>
        <a-form-item label="负责科目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            mode="multiple"
            style="width: 100%"
            placeholder="请选择科目"
            optionFilterProp = "children"
            v-model="selectedSubject">
            <a-select-option v-for="(subject,subjectindex) in subjectList" :key="subjectindex.toString()" :value="subject.id">
              {{ subject.subjectName }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="头像" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['avatar', validatorRules.avatar]" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['sex', validatorRules.sex]" :trigger-change="true" dictCode="sex" placeholder="请选择性别"/>
        </a-form-item>
        <a-form-item label="电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'mobile', validatorRules.mobile]" placeholder="请输入电话"></a-input>
        </a-form-item>
        <a-form-item label="邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'email', validatorRules.email]" placeholder="请输入邮箱"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JUpload from '@/components/jeecg/JUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import {queryAllSubjects,queryTeacherSubject} from "@/api/api"

  export default {
    name: "EduTeacherModal",
    components: { 
      JUpload,
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        subjectList:[],
        selectedSubject:[],
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
          teacherName: {rules: [
          ]},
          avatar: {rules: [
          ]},
          sex: {rules: [
          ]},
          mobile: {rules: [
            {pattern:/^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
          ]},
          email: {rules: [
           {pattern:/^([\w]+\.*)([\w]+)@[\w]+\.\w{3}(\.\w{2}|)$/, message: '请输入正确的电子邮件!'},
          ]},
        },
        url: {
          add: "/edu/user/eduTeacher/add",
          edit: "/edu/user/eduTeacher/edit",
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
      loadTeacherSubject(teacherId){
        queryTeacherSubject({teacherId:teacherId}).then((res)=>{
          if(res.success){
            this.selectedSubject = res.result;
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
        this.loadTeacherSubject(record.id);
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'teacherName','avatar','sex','mobile','email'))
        })
      },
      close () {
        this.$emit('close');
        this.selectedSubject = [];
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
            formData.selectedSubjects = this.selectedSubject.length>0?this.selectedSubject.join(","):'';

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
        this.form.setFieldsValue(pick(row,'teacherName','avatar','sex','mobile','email'))
      },

      
    }
  }
</script>