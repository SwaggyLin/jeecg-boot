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

        <a-form-item label="地区" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'area', validatorRules.area]" placeholder="请输入地区"></a-input>
        </a-form-item>
        <a-form-item label="技能编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'skillNo', validatorRules.skillNo]" placeholder="请输入技能编号"></a-input>
        </a-form-item>
        <a-form-item label="技能名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'skillName', validatorRules.skillName]" placeholder="请输入技能名称"></a-input>
        </a-form-item>
        <a-form-item label="日文名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'japaneseName', validatorRules.japaneseName]" placeholder="请输入日文名"></a-input>
        </a-form-item>
        <a-form-item label="英文名字" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'englishName', validatorRules.englishName]" placeholder="请输入英文名字"></a-input>
        </a-form-item>
        <a-form-item label="技能属性" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'skillAttr', validatorRules.skillAttr]" placeholder="请输入技能属性"></a-input>
        </a-form-item>
        <a-form-item label="技能类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'skillType', validatorRules.skillType]" placeholder="请输入技能类型"></a-input>
        </a-form-item>
        <a-form-item label="威力" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'power', validatorRules.power]" placeholder="请输入威力"></a-input>
        </a-form-item>
        <a-form-item label="命中率" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'hitRate', validatorRules.hitRate]" placeholder="请输入命中率"></a-input>
        </a-form-item>
        <a-form-item label="pp" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'skillPp', validatorRules.skillPp]" placeholder="请输入pp"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'

  export default {
    name: "PokemonSkillModal",
    components: { 
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
        validatorRules:{
        area:{},
        skillNo:{},
        skillName:{},
        japaneseName:{},
        englishName:{},
        skillAttr:{},
        skillType:{},
        power:{},
        hitRate:{},
        skillPp:{},
        },
        url: {
          add: "/pokemon/pokemonSkill/add",
          edit: "/pokemon/pokemonSkill/edit",
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
          this.form.setFieldsValue(pick(this.model,'area','skillNo','skillName','japaneseName','englishName','skillAttr','skillType','power','hitRate','skillPp'))
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
        this.form.setFieldsValue(pick(row,'area','skillNo','skillName','japaneseName','englishName','skillAttr','skillType','power','hitRate','skillPp'))
      },

      
    }
  }
</script>