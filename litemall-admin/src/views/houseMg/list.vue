<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.goodsId" clearable class="filter-item" style="width: 160px;" placeholder="请输入阿姨ID" />
      <el-input v-model="listQuery.goodsSn" clearable class="filter-item" style="width: 160px;" placeholder="请输入阿姨编号" />
      <el-input v-model="listQuery.name" clearable class="filter-item" style="width: 160px;" placeholder="请输入阿姨名称" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-edit" @click="handleCreate">添加</el-button>
      <el-button v-if="0" :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>

      <el-table-column align="center" label="阿姨ID" prop="id" />

      <el-table-column align="center" min-width="100" label="称呼" prop="nickName" />
      <el-table-column align="center" min-width="120" label="头像">
        <template slot-scope="scope">
          <el-image :src="scope.row.headUrl" fit="fill" :lazy="true" style="width: 100px; height: 100px" />
        </template>
      </el-table-column>

      <el-table-column align="center" label="性别">
        <template slot-scope="scope">
          <span v-if="scope.row.sex == 1">男</span>
          <span v-else>女</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="年龄" prop="age" />

      <el-table-column align="center" width="100" label="出生日期" prop="birthday" />

      <el-table-column align="center" label="籍贯" prop="nativePlace" />

      <el-table-column align="center" label="从业时间" prop="experience" />

      <el-table-column align="center" label="学历" prop="education" />

      <el-table-column align="center" label="审核状态">
        <template slot-scope="scope">
          <span v-if="scope.row.auditStatus==1">审核通过</span>
          <span v-else>审核失败</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="是否删除">
        <template slot-scope="scope">
          <span v-if="scope.row.isDel">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="是否展示">
        <template slot-scope="scope">
          <span v-if="scope.row.saleStatus">展示</span>
          <span v-else>不展示</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="认证状态">
        <template slot-scope="scope">
          <span v-if="scope.row.realStatus">已认证</span>
          <span v-else>未认证</span>
        </template>
      </el-table-column>

      <el-table-column align="center" min-width="92" label="电话" prop="mobile" />

      <el-table-column align="center" label="证件号" prop="idcard" />

      <el-table-column align="center" label="主要技能" prop="expertin" />

      <el-table-column align="center" label="矩阵类型" prop="type" />

      <el-table-column align="center" label="标签" prop="flag" />

      <el-table-column align="center" min-width="100" label="简介">
        <template slot-scope="scope">
          <span v-html="scope.row.des" />
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-tooltip placement="top" content="返回顶部">
      <back-to-top :visibility-height="100" />
    </el-tooltip>

  </div>
</template>

<style>
  .table-expand {
    font-size: 0;
  }
  .table-expand label {
    width: 100px;
    color: #99a9bf;
  }
  .table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
  }
  .gallery {
    width: 80px;
    margin-right: 10px;
  }
  .goods-detail-box img {
    width: 100%;
  }
</style>

<script>
import { listAunt, deleteAunt } from '@/api/housemg'
import BackToTop from '@/components/BackToTop'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'GoodsList',
  components: { BackToTop, Pagination },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        goodsSn: undefined,
        name: undefined,
        // sort: 'add_time',
        order: 'desc'
      },
      goodsDetail: '',
      detailDialogVisible: false,
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listAunt(this.listQuery).then(response => {
        this.list = response.data.data.list
        this.total = response.data.data.total
        this.listLoading = false
      }).catch(() => {
        this.list = []
        this.total = 0
        this.listLoading = false
      })
    },

    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },

    handleCreate() {
      this.$router.push({ path: '/houseMg/auntcreate' })
    },

    handleUpdate(row) {
      this.$router.push({ path: '/houseMg/auntedit', query: { id: row.id }})
    },

    showDetail(detail) {
      this.goodsDetail = detail
      this.detailDialogVisible = true
    },

    handleDelete(row) {
      this.$confirm('确定删除这条记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteAunt(row.id).then(response => {
          this.$notify.success({
            title: '成功',
            message: '删除成功'
          })
          this.getList()
        }).catch(response => {
          this.$notify.error({
            title: '失败',
            message: response.data.errmsg
          })
        })
      }).catch(() => {

      })
    },

    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['阿姨ID', '阿姨编号', '名称', '专柜价格', '当前价格', '是否新品', '是否热品', '是否在售', '首页主图', '宣传图片列表', '阿姨介绍', '详细介绍', '阿姨图片', '阿姨单位', '关键字', '类目ID', '品牌商ID']
        const filterVal = ['id', 'goodsSn', 'name', 'counterPrice', 'retailPrice', 'isNew', 'isHot', 'isOnSale', 'listPicUrl', 'gallery', 'brief', 'detail', 'picUrl', 'goodsUnit', 'keywords', 'categoryId', 'brandId']
        excel.export_json_to_excel2(tHeader, this.list, filterVal, '阿姨信息')
        this.downloadLoading = false
      })
    }
  }
}
</script>
