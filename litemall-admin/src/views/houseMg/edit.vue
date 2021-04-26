<template>
    <div class="app-container">
        <el-card class="box-card">
            <h3>编辑阿姨</h3>
            <el-form ref="goods" :rules="rules" :model="auntInfo" label-width="150px">
                <el-row>
                    <el-form-item label="头像" prop="headUrl">
                        <el-upload class="avatar-uploader" action="https://aimajiazheng.com/api/wx/storage/create" :show-file-list="false" name="file" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                            <img v-if="auntInfo.headUrl" :src="auntInfo.headUrl" class="avatar" />
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                </el-row>

                <el-row>
                    <el-col :span="12">
                        <el-form-item label="姓名" prop="nickName">
                            <el-col :span="16">
                                <el-input v-model="auntInfo.nickName" />
                            </el-col>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="民族" prop="nation">
                            <el-col :span="16">
                            <el-select placeholder="请选择状态" v-model="auntInfo.nation" style="width:100%;">
                                <el-option v-for="(item, idx) in minzuList" :key="idx" :label="item.name" :value="item.name" />
                            </el-select>
                            </el-col>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item label="籍贯" prop="nativePlace">
                            <el-col :span="16">
                            <el-select placeholder="请选择状态" v-model="auntInfo.nativePlace" style="width:100%;">
                                <el-option v-for="(item, idx) in shenfenList" :key="idx" :label="item.name" :value="item.name" />
                            </el-select>
                            </el-col>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :offset="0">
                        <el-form-item label="年龄" prop="age">
                            <el-col :span="16">
                            <el-input v-model="auntInfo.age" />
                            </el-col>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item label="生肖" prop="zodiac">
                            <el-col :span="16">
                            <el-select placeholder="请选择状态" v-model="auntInfo.zodiac" style="width:100%;">
                                <el-option v-for="(item, idx) in shuxiangList" :key="idx" :label="item" :value="item" />
                            </el-select>
                            </el-col>
                        </el-form-item>
                    </el-col>

                    <el-col :span="12" :offset="0">
                        <el-form-item label="星座" prop="constellation">
                            <el-col :span="16">
                            <el-select placeholder="请选择状态" v-model="auntInfo.constellation" style="width:100%;">
                                <el-option v-for="(item, idx) in xingzuoList" :key="idx" :label="item" :value="item" />
                            </el-select>
                            </el-col>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item label="学历" prop="education">
                            <el-col :span="16">
                            <el-select placeholder="请选择状态" v-model="auntInfo.education" style="width:100%;">
                                <el-option v-for="(item, idx) in xueliList" :key="idx" :label="item" :value="item" />
                            </el-select>
                            </el-col>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :offset="0">
                        <el-form-item label="电话" prop="mobile">
                            <el-col :span="16">
                            <el-input v-model="auntInfo.mobile" />
                            </el-col>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item label="从业时长" prop="experience">
                            <el-col :span="16">
                            <el-input v-model="auntInfo.experience">
                                <template slot="append">年</template>
                            </el-input>
                            </el-col>
                        </el-form-item>
                    </el-col>

                    <el-col :span="12" :offset="0">
                        <el-form-item label="出生日期" prop="birthday">
                            <el-col :span="16">
                            <el-date-picker style="width:100%" v-model="auntInfo.birthday" value-format="yyyy-MM-dd" type="date" placeholder="选择日期" />
                            </el-col>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item label="证件号" prop="idcard">
                            <el-col :span="16">
                            <el-input v-model="auntInfo.idcard" />
                            </el-col>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :offset="0">
                        <el-form-item label="性别" prop="sex">
                            <el-radio-group v-model="auntInfo.sex">
                                <el-radio label="1">男</el-radio>
                                <el-radio label="0">女</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item label="审核状态" prop="auditStatus">
                            <el-radio-group v-model="auntInfo.auditStatus">
                                <el-radio :label="1">通过</el-radio>
                                <el-radio :label="0">拒绝</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>

                    <el-col :span="12" :offset="0">
                        <el-form-item label="认证状态" prop="realStatus">
                            <el-radio-group v-model="auntInfo.realStatus">
                                <el-radio :label="1">已认证</el-radio>
                                <el-radio :label="0">未认证</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item label="是否展示" prop="saleStatus">
                            <el-radio-group v-model="auntInfo.saleStatus">
                                <el-radio :label="1">是</el-radio>
                                <el-radio :label="0">否</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>

                    <el-col :span="12" :offset="0">
                        <el-form-item label="矩阵类别" prop="type">
                            <el-col :span="16">
                            <el-select placeholder="请选择状态" v-model="auntInfo.type" style="width:100%">
                                <el-option v-for="(item, idx) in dictType1List" :key="idx" :label="item.dictValue" :value="item.dictValue" />
                            </el-select>
                            </el-col>
                        </el-form-item>
                    </el-col>
                </el-row>


                <el-form-item label="主要技能" prop="expertin">
                    <el-col :span="7">
                    <el-select placeholder="请选择状态" v-model="auntInfo.expertin" style="width:100%">
                        <el-option v-for="(item, idx) in dictType2List" :key="idx" :label="item.dictValue" :value="item.dictValue" />
                    </el-select>
                    </el-col>
                </el-form-item>

                <el-form-item label="个性标签" prop="flag">
                    <el-checkbox-group v-model="flagList">
                        <el-checkbox :label="obj.dictValue" v-for="(obj, idx) in dictType3List" :key="idx"></el-checkbox>
                    </el-checkbox-group>
                </el-form-item>

                <el-form-item label="身份认证" prop="identityApprove">
                    <el-input v-model="auntInfo.identityApprove" />
                </el-form-item>
                <el-form-item label="技能认证" prop="skillApprove">
                    <el-input v-model="auntInfo.skillApprove" />
                </el-form-item>
                <el-form-item label="背景调查" prop="backgroundApprove">
                    <el-input v-model="auntInfo.backgroundApprove" />
                </el-form-item>
                <el-form-item label="职业培训" prop="trainApprove">
                    <el-input v-model="auntInfo.trainApprove"/>
                </el-form-item>
                <el-form-item label="简历真实" prop="resumeApprove">
                    <el-input v-model="auntInfo.resumeApprove" />
                </el-form-item>
                <el-form-item label="上户保险" prop="insuranceApprove">
                    <el-input v-model="auntInfo.insuranceApprove" />
                </el-form-item>

                <el-form-item label="求职意向">
                    <el-input v-model="auntInfo.jobIntention" type="textarea" :autosize="{ minRows: 2, maxRows: 4 }" />
                </el-form-item>
                <el-form-item label="自我介绍">
                    <el-input v-model="auntInfo.selfIntroduction" type="textarea" :autosize="{ minRows: 2, maxRows: 4 }" />
                </el-form-item>
                <el-form-item label="工作经历">
                  <el-input v-model="auntInfo.workExperience" type="textarea" :autosize="{ minRows: 2, maxRows: 8 }" />
                    <!--<editor v-model="auntInfo.workExperience" :init="editorInit" />-->
                </el-form-item>
                <el-form-item label="培训经历">
                  <el-input v-model="auntInfo.des" type="textarea" :autosize="{ minRows: 2, maxRows: 8 }" />
                    <!--<editor v-model="auntInfo.des" :init="editorInit" />-->
                </el-form-item>

                <el-form-item label="个人展示" prop="nativePlace">
                    <el-upload action="https://aimajiazheng.com/api/wx/storage/create" :file-list="personalPresentationList" list-type="picture-card" :on-remove="handlePersonShowRemove" :on-success="handlePersonShowSuccess" name="file">
                        <i class="el-icon-plus"></i>
                    </el-upload>
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
import { listDict } from "@/api/dict";

import MINZUArr from "@/minzu.js";
import SHENFENArr from "@/shenfen.js";


import { createStorage, uploadPath } from '@/api/storage'
import Editor from '@tinymce/tinymce-vue'
import { MessageBox } from 'element-ui'
import { getToken } from '@/utils/auth'

export default {
  name: 'GoodsCreate',
  components: { Editor },

  data() {
    return {
       xueliList: [ "小学", "初中", "高中", "大专", "本科", "研究生", "博士", "博士后", ],
      xingzuoList: [ "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座", ],
      shuxiangList: [ "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪", ],
      shenfenList: [],
      minzuList: [],
      dictType1List: [],
      dictType2List: [],
      dictType3List: [],
      auntInfo: {
        age: 30,
        headUrl: "",
        auditStatus: 0,
        birthday: "",
        des: "",
        education: "",
        experience: "",
        expertin: "",
        isDel: 0,
        nativePlace: "",
        nickName: "",
        realStatus: 0,
        saleStatus: 0,
        sex: "0",
        flag: [],
        nation: "",
        personalPresentation:'',//个人照片  都好隔开
      },
        personalPresentationList:[],//个人照片
        flagList:[],

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
    this.shenfenList = SHENFENArr;
    this.minzuList = MINZUArr;

    this.netFetchDictType1();
    this.netFetchDictType2();
    this.netFetchDictType3();

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
        let list = self.auntInfo.personalPresentation.split(",")
        self.personalPresentationList = []
        list.map((url,idx)=>{
          self.personalPresentationList.push({url})
        })
        self.flagList = self.auntInfo.flag.split(",")
      }).catch(e => {
        console.error(e)
      })
    },


    handleUpdate: function() {
      let list = []
      this.personalPresentationList.forEach(e=>{
        list.push(e.url)
      })
      this.auntInfo.flag = this.flagList.toString();
      this.auntInfo.personalPresentation = list.toString();
      editAunt(this.auntInfo)
        .then((response) => {
          this.$notify.success({
            title: '成功',
            message: '更新成功'
          })
          this.$router.push({ path: '/houseMg/auntlist' })
        })
        .catch((response) => {
          MessageBox.alert('业务错误：' + response.data.errmsg, '警告', {
            confirmButtonText: '确定',
            type: 'error'
          })
        })
    },

    netFetchDictType1() {
      listDict({ dictType: 3 })
        .then((response) => {
          this.dictType1List = response.data.data.list;
        })
        .catch(() => {});
    },

    netFetchDictType2() {
      listDict({ dictType: 2 }).then((res) => {
        this.dictType2List = res.data.data.list;
      });
    },
    netFetchDictType3() {
      listDict({ dictType: 3 }).then((res) => {
        this.dictType3List = res.data.data.list;
      });
    },

    handleAvatarSuccess(res, file) {
      this.auntInfo.headUrl = res.data.url;
    },

    handlePersonShowRemove(file, fileList) {//移除图片传给后台的数据
        this.personalPresentationList=[];
        fileList.map((obj,idx)=>{
            this.personalPresentationList.push(obj.response.data.url)
        })

        console.log(this.personalPresentationList);
    },
    handlePersonShowSuccess(res, file) {
      this.personalPresentationList.push({url:res.data.url});
    },

  }
}
</script>
