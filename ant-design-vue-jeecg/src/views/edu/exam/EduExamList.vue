<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('考试表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{fixed:true,selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" v-if="record.examState != '2'"/>
          <a-dropdown v-if="record.examState != '2'">
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item v-if="record.examState == '1'">
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
              <a-menu-item v-if="record.examState == '3'">
                  <a @click="handleEditExamGrade(record)">录入成绩</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <edu-exam-modal ref="modalForm" @ok="modalFormOk"></edu-exam-modal>
    <edu-exam-grade-modal ref="editExamGradeTable"></edu-exam-grade-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import EduExamModal from './modules/EduExamModal'
  import EduExamGradeModal from './modules/EduExamGradeModal'

  export default {
    name: "EduExamList",
    mixins:[JeecgListMixin],
    components: {
      EduExamModal,
      EduExamGradeModal
    },
    data () {
      return {
        description: '考试表管理页面',
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
            title:'考试名称',
            align:"center",
            dataIndex: 'examName'
          },
          {
            title:'学期',
            align:"center",
            dataIndex: 'examPeriod_dictText'
          },
          {
            title:'考试科目',
            align:"center",
            dataIndex: 'subjectName'
          },
          {
            title:'考试类型',
            align:"center",
            dataIndex: 'examType_dictText'
          },
          {
            title:'考试时间',
            align:"center",
            customRender:(text,record)=>{
              return record.startTime+' － '+record.endTime
            }
          },
          {
            title:'考试状态',
            align:"center",
            dataIndex: 'examState_dictText'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/edu/exam/eduExam/list",
          delete: "/edu/exam/eduExam/delete",
          deleteBatch: "/edu/exam/eduExam/deleteBatch",
          exportXlsUrl: "/edu/exam/eduExam/exportXls",
          importExcelUrl: "edu/exam/eduExam/importExcel",
        },
        dictOptions:{
         examState:[],
        },
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      handleEditExamGrade: function (record) {
        this.$refs.editExamGradeTable.edit(record);
        this.$refs.editExamGradeTable.title = "录入成绩";
      }  
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>