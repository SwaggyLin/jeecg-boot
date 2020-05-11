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
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <j-editable-table
        ref="editableTable"
        :loading="loading"
        :columns="columns"
        :dataSource="dataSource"
        :rowNumber=true
        :rowSelection=true
        :actionButton=true
        :dragSort="true"
        style="margin-top: 8px;"
        @selectRowChange="handleSelectRowChange">

        <template v-slot:action="props">
          <a @click="handleDelete(props)">{{ props.text }}</a>
        </template>

      </j-editable-table>
    </div>

  </a-modal>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JEditableTable from '@/components/jeecg/JEditableTable'

  export default {
    name: "EduExamGradeModal",
    mixins:[JeecgListMixin],
    components: {
      JEditableTable
    },
    data () {
      return {
        loading: false,
        title:"操作",
        description: '考试成绩表管理页面',
        visible:false,
        width:'100%',
        bodyStyle:{
          padding: "0",
          height:(window.innerHeight)+"px",
          "overflow-y":"auto",
        },
        fullScreen:true,
        confirmLoading:false,
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'评卷人',
            align:"center",
            dataIndex: 'createBy'
          },
          {
            title:'评卷日期',
            align:"center",
            dataIndex: 'createTime'
          },
          {
            title:'考试id',
            align:"center",
            dataIndex: 'examId'
          },
          {
            title:'学生id',
            align:"center",
            dataIndex: 'studentId'
          },
          {
            title:'班级id',
            align:"center",
            dataIndex: 'classId'
          },
          {
            title:'成绩',
            align:"center",
            dataIndex: 'grade'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        dataSource: [],
        selectedRowIds: [],
        url: {
          list: "/edu/exam/eduExamGrade/list"       
        }
      }
    },
    computed: {
    },
    methods: {
      edit (record) {
        if(record.hasOwnProperty("id")){
          this.visible=true;
        }else{
          this.$message.warning("参数错误!")
        }
        //this.model = Object.assign({}, record);
        // this.$nextTick(() => {
        //   this.form.setFieldsValue(pick(this.model,'examName','examType','startTime','endTime','examState','examPeriod','subjectId'))
        // })
      },
      handleSelectRowChange(selectedRowIds) {
        this.selectedRowIds = selectedRowIds
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>