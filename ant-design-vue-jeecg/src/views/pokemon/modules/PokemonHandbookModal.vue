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

        <a-form-item label="图鉴id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'zukanId', validatorRules.zukanId]" placeholder="请输入图鉴id"></a-input>
        </a-form-item>
        <a-form-item label="图鉴细级id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'zukanSubId', validatorRules.zukanSubId]" placeholder="请输入图鉴细级id"></a-input>
        </a-form-item>
        <a-form-item label="宝可梦名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'pokemonName', validatorRules.pokemonName]" placeholder="请输入宝可梦名称"></a-input>
        </a-form-item>
        <a-form-item label="详细名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'pokemonSubName', validatorRules.pokemonSubName]" placeholder="请输入详细名称"></a-input>
        </a-form-item>
        <a-form-item label="宝可梦重量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'weight', validatorRules.weight]" placeholder="请输入宝可梦重量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="宝可梦高度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'height', validatorRules.height]" placeholder="请输入宝可梦高度" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="图片地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['fileName']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="宝可梦类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag type="list_multi" v-decorator="['pokemonTypeId']" :trigger-change="true" dictCode="" placeholder="请选择宝可梦类型"/>
        </a-form-item>
        <a-form-item label="宝可梦类型名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'pokemonTypeName', validatorRules.pokemonTypeName]" placeholder="请输入宝可梦类型名称"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JUpload from '@/components/jeecg/JUpload'
  import JMultiSelectTag from "@/components/dict/JMultiSelectTag"

  export default {
    name: "PokemonHandbookModal",
    components: { 
      JUpload,
      JMultiSelectTag,
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
        zukanId:{},
        zukanSubId:{},
        pokemonName:{},
        pokemonSubName:{},
        weight:{},
        height:{},
        fileName:{},
        pokemonTypeId:{},
        pokemonTypeName:{},
        },
        url: {
          add: "/pokemon/pokemonHandbook/add",
          edit: "/pokemon/pokemonHandbook/edit",
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
          this.form.setFieldsValue(pick(this.model,'zukanId','zukanSubId','pokemonName','pokemonSubName','weight','height','fileName','pokemonTypeId','pokemonTypeName'))
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
        this.form.setFieldsValue(pick(row,'zukanId','zukanSubId','pokemonName','pokemonSubName','weight','height','fileName','pokemonTypeId','pokemonTypeName'))
      },

      
    }
  }
</script>