<template>
  <div class="app-container">
    <el-card class="box-card">
      <h3>编辑阿姨</h3>
      <el-form ref="goods" :rules="rules" :model="goods" label-width="150px">
        <el-form-item label="称呼" prop="nickName">
          <el-input v-model="auntInfo.nickName" />
        </el-form-item>

        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="auntInfo.sex">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="年龄" prop="age">
          <el-input v-model="auntInfo.age" />
        </el-form-item>

        <el-form-item label="生日" prop="birthday">
          <el-input v-model="auntInfo.age" />
        </el-form-item>

        <el-form-item label="籍贯" prop="nativePlace">
          <el-input v-model="auntInfo.nativePlace" />
        </el-form-item>

        <el-form-item label="从业时长" prop="experience">
          <el-input v-model="auntInfo.experience">
            <template slot="append">年</template>
          </el-input>
        </el-form-item>

        <el-form-item label="学历" prop="education">
          <el-input v-model="auntInfo.education" />
        </el-form-item>

        <el-form-item label="审核状态" prop="auditStatus">
          <el-input v-model="auntInfo.auditStatus" />
        </el-form-item>

        <el-form-item label="是否删除" prop="isDel">
          <el-radio-group v-model="auntInfo.isDel">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="是否展示" prop="saleStatus">
          <el-radio-group v-model="auntInfo.saleStatus">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="认证状态" prop="realStatus">
          <el-radio-group v-model="auntInfo.realStatus">
            <el-radio :label="1">认证成功</el-radio>
            <el-radio :label="0">认证失败</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="矩阵类型" prop="type">
          <el-select v-model="auntInfo.type" value-key="" placeholder="请选择" clearable filterable>
            <el-option
              v-for="item in 3"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="电话" prop="mobile">
          <el-input v-model="auntInfo.mobile" />
        </el-form-item>

        <el-form-item label="证件号" prop="idcard">
          <el-input v-model="auntInfo.idcard" />
        </el-form-item>

        <el-form-item label="擅长内容" prop="expertin">
          <el-input v-model="auntInfo.expertin" />
        </el-form-item>

        <el-form-item label="类别" prop="type">
          <el-input v-model="auntInfo.type" />
        </el-form-item>

        <el-form-item label="标签" prop="flag">
          <el-input v-model="auntInfo.flag" />
        </el-form-item>

        <el-form-item label="简介">
          <editor v-model="auntInfo.des" :init="editorInit" />
        </el-form-item>

      </el-form>
    </el-card>

    <!-- dev -->

    <div class="op-container">
      <el-button type="primary" @click="handleUpdate">更新</el-button>
    </div>
  </div>
</template>

<style>
.el-card {
  margin-bottom: 10px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.input-new-keyword {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}

.avatar-uploader .el-upload {
  width: 145px;
  height: 145px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #20a0ff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}

.avatar {
  width: 145px;
  height: 145px;
  display: block;
}
</style>

<script>
import { editAunt, detailAunt } from '@/api/housemg'
import { createStorage, uploadPath } from '@/api/storage'
import Editor from '@tinymce/tinymce-vue'
import { MessageBox } from 'element-ui'
import { getToken } from '@/utils/auth'

export default {
  name: 'GoodsCreate',
  components: { Editor },

  data() {
    return {
      auntInfo: {
        age: 30,
        auditStatus: 0,
        birthday: '',
        des: '',
        education: '',
        experience: '',
        expertin: '',
        isDel: false,
        nativePlace: '',
        nickName: '',
        realStatus: false,
        saleStatus: false,
        sex: '0'
      },

      uploadPath,
      newKeywordVisible: false,
      newKeyword: '',
      keywords: [],
      categoryList: [],
      brandList: [],
      goods: {
        picUrl: '',
        gallery: [],
        isHot: false,
        isNew: true,
        isOnSale: true
      },
      specVisiable: false,
      specForm: { specification: '', value: '', picUrl: '' },
      multipleSpec: false,
      specifications: [{ specification: '规格', value: '标准', picUrl: '' }],
      productVisiable: false,
      productForm: {
        id: 0,
        specifications: [],
        price: 0.0,
        number: 0,
        url: ''
      },
      products: [{ id: 0, specifications: ['标准'], price: 0.0, number: 0, url: '' }],
      attributeVisiable: false,
      attributeForm: { attribute: '', value: '' },
      attributes: [],
      rules: {
        goodsSn: [{ required: true, message: '阿姨编号不能为空', trigger: 'blur' }],
        name: [{ required: true, message: '阿姨名称不能为空', trigger: 'blur' }]
      },
      editorInit: {
        language: 'zh_CN',
        height: 500,
        convert_urls: false,
        plugins: [
          'advlist anchor autolink autosave code codesample colorpicker colorpicker contextmenu directionality emoticons fullscreen hr image imagetools importcss insertdatetime link lists media nonbreaking noneditable pagebreak paste preview print save searchreplace spellchecker tabfocus table template textcolor textpattern visualblocks visualchars wordcount'
        ],
        toolbar: [
          'searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote undo redo removeformat subscript superscript code codesample',
          'hr bullist numlist link image charmap preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen'
        ],
        images_upload_handler: function(blobInfo, success, failure) {
          const formData = new FormData()
          formData.append('file', blobInfo.blob())
          createStorage(formData)
            .then((res) => {
              success(res.data.data.url)
            })
            .catch(() => {
              failure('上传失败，请重新上传')
            })
        }
      }
    }
  },
  computed: {
    headers() {
      return {
        'X-Litemall-Admin-Token': getToken()
      }
    }
  },
  created() {
    this.init()
  },

  methods: {
    init: function() {
      if (this.$route.query.id == null) {
        return
      }
      const auntId = this.$route.query.id
      const self = this
      detailAunt(auntId).then(response => {
        self.auntInfo = response.data.data
      }).catch(e => {
        console.error(e)
      })
    },

    handleCategoryChange(value) {
      this.goods.categoryId = value[value.length - 1]
    },
    handleCancel: function() {
      this.$store.dispatch('tagsView/delView', this.$route)
      this.$router.push({ path: '/goods/list' })
    },
    handleUpdate: function() {
      editAunt(this.auntInfo)
        .then((response) => {
          this.$notify.success({
            title: '成功',
            message: '更新成功'
          })
          this.$router.push({ path: '/houseMg/list' })
        })
        .catch((response) => {
          MessageBox.alert('业务错误：' + response.data.errmsg, '警告', {
            confirmButtonText: '确定',
            type: 'error'
          })
        })
    },
    handleClose(tag) {
      this.keywords.splice(this.keywords.indexOf(tag), 1)
      this.goods.keywords = this.keywords.toString()
    },
    showInput() {
      this.newKeywordVisible = true
      this.$nextTick((_) => {
        this.$refs.newKeywordInput.$refs.input.focus()
      })
    },
    handleInputConfirm() {
      const newKeyword = this.newKeyword
      if (newKeyword) {
        this.keywords.push(newKeyword)
        this.goods.keywords = this.keywords.toString()
      }
      this.newKeywordVisible = false
      this.newKeyword = ''
    },
    uploadPicUrl: function(response) {
      this.goods.picUrl = response.data.url
    },
    uploadOverrun: function() {
      this.$message({
        type: 'error',
        message: '上传文件个数超出限制!最多上传5张图片!'
      })
    },
    handleGalleryUrl(response, file, fileList) {
      if (response.errno === 0) {
        this.goods.gallery.push(response.data.url)
      }
    },
    handleRemove: function(file, fileList) {
      for (var i = 0; i < this.goods.gallery.length; i++) {
        // 这里存在两种情况
        // 1. 如果所删除图片是刚刚上传的图片，那么图片地址是file.response.data.url
        //    此时的file.url虽然存在，但是是本机地址，而不是远程地址。
        // 2. 如果所删除图片是后台返回的已有图片，那么图片地址是file.url
        var url
        if (file.response === undefined) {
          url = file.url
        } else {
          url = file.response.data.url
        }

        if (this.goods.gallery[i] === url) {
          this.goods.gallery.splice(i, 1)
        }
      }
    },
    specChanged: function(label) {
      if (label === false) {
        this.specifications = [{ specification: '规格', value: '标准', picUrl: '' }]
        this.products = [
          { id: 0, specifications: ['标准'], price: 0.0, number: 0, url: '' }
        ]
      } else {
        this.specifications = []
        this.products = []
      }
    },
    uploadSpecPicUrl: function(response) {
      this.specForm.picUrl = response.data.url
    },
    handleSpecificationShow() {
      this.specForm = { specification: '', value: '', picUrl: '' }
      this.specVisiable = true
    },
    handleSpecificationAdd() {
      var index = this.specifications.length - 1
      for (var i = 0; i < this.specifications.length; i++) {
        const v = this.specifications[i]
        if (v.specification === this.specForm.specification) {
          if (v.value === this.specForm.value) {
            this.$message({
              type: 'warning',
              message: '已经存在规格值:' + v.value
            })
            this.specForm = {}
            this.specVisiable = false
            return
          } else {
            index = i
          }
        }
      }

      this.specifications.splice(index + 1, 0, this.specForm)
      this.specVisiable = false

      this.specToProduct()
    },
    handleSpecificationDelete(row) {
      const index = this.specifications.indexOf(row)
      this.specifications.splice(index, 1)
      this.specToProduct()
    },
    specToProduct() {
      if (this.specifications.length === 0) {
        return
      }
      // 根据specifications创建临时规格列表
      var specValues = []
      var spec = this.specifications[0].specification
      var values = []
      values.push(0)

      for (var i = 1; i < this.specifications.length; i++) {
        const aspec = this.specifications[i].specification

        if (aspec === spec) {
          values.push(i)
        } else {
          specValues.push(values)
          spec = aspec
          values = []
          values.push(i)
        }
      }
      specValues.push(values)

      // 根据临时规格列表生产货品规格
      // 算法基于 https://blog.csdn.net/tyhj_sf/article/details/53893125
      var productsIndex = 0
      var products = []
      var combination = []
      var n = specValues.length
      for (var s = 0; s < n; s++) {
        combination[s] = 0
      }
      var index = 0
      var isContinue = false
      do {
        var specifications = []
        for (var x = 0; x < n; x++) {
          var z = specValues[x][combination[x]]
          specifications.push(this.specifications[z].value)
        }
        products[productsIndex] = {
          id: productsIndex,
          specifications: specifications,
          price: 0.0,
          number: 0,
          url: ''
        }
        productsIndex++

        index++
        combination[n - 1] = index
        for (var j = n - 1; j >= 0; j--) {
          if (combination[j] >= specValues[j].length) {
            combination[j] = 0
            index = 0
            if (j - 1 >= 0) {
              combination[j - 1] = combination[j - 1] + 1
            }
          }
        }
        isContinue = false
        for (var p = 0; p < n; p++) {
          if (combination[p] !== 0) {
            isContinue = true
          }
        }
      } while (isContinue)

      this.products = products
    },
    handleProductShow(row) {
      this.productForm = Object.assign({}, row)
      this.productVisiable = true
    },
    uploadProductUrl: function(response) {
      this.productForm.url = response.data.url
    },
    handleProductEdit() {
      for (var i = 0; i < this.products.length; i++) {
        const v = this.products[i]
        if (v.id === this.productForm.id) {
          this.products.splice(i, 1, this.productForm)
          break
        }
      }
      this.productVisiable = false
    },
    handleAttributeShow() {
      this.attributeForm = {}
      this.attributeVisiable = true
    },
    handleAttributeAdd() {
      this.attributes.unshift(this.attributeForm)
      this.attributeVisiable = false
    },
    handleAttributeDelete(row) {
      const index = this.attributes.indexOf(row)
      this.attributes.splice(index, 1)
    }
  }
}
</script>
