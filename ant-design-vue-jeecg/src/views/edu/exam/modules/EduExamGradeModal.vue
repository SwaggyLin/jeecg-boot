<template>
  <a-modal
    :title="title"
    :fullScreen="fullScreen"
    :visible="visible"
    :width="width"
    :bodyStyle ="bodyStyle"
    :closable="false"
    cancelText="关闭"
    @ok="()=>this.visible=false"
    @cancel="()=>this.visible=false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    

    <!-- table区域-begin -->
    <div>

      <j-editable-table
        ref="editableTable"
        :loading="loading"
        :columns="columns"
        :dataSource="dataSource"
        :rowNumber=true
        :rowSelection=true
        :actionButton=false
        :dragSort="true"
        dragSortKey="seatNum"
        style="margin-top: 8px;"
        >

        <template v-slot:action="props">
          <a @click="handleDelete(props)">{{ props.text }}</a>
        </template>

      </j-editable-table>
    </div>

  </a-modal>
</template>

<script>

  import { FormTypes } from '@/utils/JEditableTableUtil'
  import JEditableTable from '@/components/jeecg/JEditableTable'
  import { getAction,httpAction } from '@/api/manage'


  export default {
    name: "EduExamGradeModal",
    components: {
      JEditableTable
    },
    data () {
      return {
        loading: false,
        title:"操作",
        description: '考试成绩表管理页面',
        visible:false,
        width:'99%',
        bodyStyle:{
          top:"0",
          padding: "0",
          height:(window.innerHeight)+"px",
          "overflow-y":"auto"
        },
        fullScreen:true,
        confirmLoading:false,
        // 表头
        columns: [
          // {
          //   title: '#',
          //   dataIndex: '',
          //   key:'rowIndex',
          //   width:60,
          //   align:"center",
          //   customRender:function (t,r,index) {
          //     return parseInt(index)+1;
          //   },
          //   type:FormTypes.normal
          // },
          // {
          //   title:'评卷日期',
          //   align:"center",
          //   key: 'createTime',
          //   type:FormTypes.datetime
          // },
          {
            title:'考试名称',
            align:"center",
            key: 'examName',
            type:FormTypes.normal,
          },
          {
            title:'座号',
            align:"center",
            key: 'seatNum',
            type:FormTypes.normal
          },
          {
            title:'学生',
            align:"center",
            key: 'studentName',
            type:FormTypes.normal
          },
          {
            title:'班级',
            align:"center",
            key: 'className',
            type:FormTypes.normal
          },
          {
            title:'成绩',
            align:"center",
            type: FormTypes.inputNumber,
            allowInput: true,
            defaultValue: '',
            key: 'grade',
            props:{
              onBlur:(row)=>{
                this.saveGrade(row);
              }
            },
            // 表单验证规则
            validateRules: [
                {
                    required: true, // 必填
                    message: '${title}不能为空' // 提示的文本
                },
                {
                    pattern: /(^(\d|[1-9]\d)(\.\d{1,2})?$)|(^100$)/, // 0-100两位小数正则
                    message: '${title}必须以为0-100两位小数'
                },
                {
                // 自定义函数校验 handler
                handler(type, value, row, column, callback, target) {
                    // type 触发校验的类型（input、change、blur）
                    // value 当前校验的值
                    // callback(flag, message) 方法必须执行且只能执行一次
                    //          flag = 是否通过了校验，不填写或者填写 null 代表不进行任何操作
                    //          message = 提示的类型，默认使用配置的 message
                    // target 行编辑的实例对象

                    if (type === 'blur') {
                        //保存成绩
                        let { error, values } =target.getValuesSync({ validate: true, rowIds: [row.id] })
                        if (error === 0) {
                            console.log(column)
                            column.props.onBlur(values[0]);
                            callback(true) // true = 通过验证
                        } else {
                            callback()
                        }
                    } else {
                        callback() // 不填写或者填写 null 代表不进行任何操作
                    }
                },
                message: '${title}默认提示'
            }
            ]
          }
          // {
          //   title:'评卷人',
          //   align:"center",
          //   key: 'createBy',
          //   type:FormTypes.normal
          // },
          // {
          //   title: '操作',
          //   key: 'action',
          //   align:"center",
          //   //scopedSlots: { customRender: 'action' },
          //   type: FormTypes.slot,
          //   slotName: 'action',
          // }
        ],
        dataSource: [],
        selectedRowIds: [],
        url: {
          list: "/edu/exam/eduExamGrade/list" ,
          edit: "/edu/exam/eduExamGrade/edit"      
        }
      }
    },
    computed: {
      
    },
    methods: {
      edit (record) {
        if(record.hasOwnProperty("id")){
          this.visible=true;
          this.loadData(record.id)
        }else{
          this.$message.warning("参数错误!")
        }
      },
      loadData(examId){
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        // var params = this.getQueryParams();//查询条件
        var params ={};
        params.examId=examId;
        this.loading=true;
        getAction(this.url.list, params).then((res) => {
        if (res.success) {
          this.dataSource = res.result.records;
          //this.ipagination.total = res.result.total;
          console.log()
        }
        if(res.code===510){
          this.$message.warning(res.message)
        }
        this.loading = false;
      })
      },
      saveGrade(record){
        const that = this;
        console.log(record);
        that.confirmLoading = true;
        httpAction(this.url.edit,record,'put').then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
      },
      // getQueryParams() {
      //   //获取查询条件
      //   let sqp = {}
      //   if(this.superQueryParams){
      //     sqp['superQueryParams']=encodeURI(this.superQueryParams)
      //   }
      //   var param = Object.assign(sqp, this.queryParam, this.isorter ,this.filters);
      //   param.field = this.getQueryField();
      //   param.pageNo = this.ipagination.current;
      //   param.pageSize = this.ipagination.pageSize;
      //   return filterObj(param);
      // },
      // handleValueChange(event) {
      //   const { type, row, column, value, target } = event;
      //   console.log(value)
      // }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>