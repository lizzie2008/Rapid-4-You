<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col
        :xs="24"
        :sm="24"
        :md="8"
        :lg="6"
        :xl="5"
        style="margin-bottom: 10px"
      >
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div>
            <div style="text-align: center">
              <div class="el-upload">
                <img
                  :src="
                    user.avatarName
                      ? baseApi + '/avatar/' + user.avatarName
                      : Avatar
                  "
                  title="点击上传头像"
                  class="avatar"
                >
                <!-- <myUpload
                  v-model="show"
                  :headers="headers"
                  :url="updateAvatarApi"
                  @crop-upload-success="cropUploadSuccess"
                /> -->
              </div>
            </div>
            <ul class="user-info">
              <li>
                <div style="height: 100%">
                  <svg-icon icon-class="login" /> 登录账号
                  <div class="user-right">{{ user.username }}</div>
                </div>
              </li>
              <li>
                <svg-icon icon-class="user1" /> 用户昵称
                <div class="user-right">{{ user.nickName }}</div>
              </li>
              <li>
                <svg-icon icon-class="dept" /> 所属部门
                <div class="user-right">{{ user.dept.name }}</div>
              </li>
              <li>
                <svg-icon icon-class="phone" /> 手机号码
                <div class="user-right">{{ user.phone }}</div>
              </li>
              <li>
                <svg-icon icon-class="anq" /> 安全设置
                <div class="user-right">
                  <a @click="$refs.pass.dialog = true">修改密码</a>
                </div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="16" :lg="18" :xl="19">
        <!--    用户资料    -->
        <el-card class="box-card">
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="用户资料" name="first">
              <el-form
                ref="form"
                :model="form"
                :rules="rules"
                style="margin-top: 10px"
                size="small"
                label-width="65px"
              >
                <el-form-item label="昵称" prop="nickName">
                  <el-input v-model="form.nickName" style="width: 35%" />
                  <span
                    style="color: #c0c0c0; margin-left: 10px"
                  >用户昵称不作为登录使用</span>
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                  <el-input v-model="form.phone" style="width: 35%" />
                  <span
                    style="color: #c0c0c0; margin-left: 10px"
                  >手机号码不能重复</span>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                  <el-input
                    v-model="form.email"
                    auto-complete="on"
                    style="width: 35%"
                  />
                </el-form-item>
                <el-form-item label="性别">
                  <el-radio-group v-model="form.gender" style="width: 178px">
                    <el-radio label="男">男</el-radio>
                    <el-radio label="女">女</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="">
                  <el-button
                    :loading="saveLoading"
                    size="mini"
                    type="primary"
                    @click="doSubmit"
                  >保存配置</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
    <updatePass ref="pass" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import updatePass from './components/updatePass'
import { getToken } from '@/utils/auth'
import store from '@/store'
import { isvalidPhone, validEmail } from '@/utils/validate'
import { editProfile } from '@/api/system/user'
import Avatar from '@/assets/images/avatar.png'

// 自定义验证
const validPhone = (rule, value, callback) => {
  if (!value) {
    callback(new Error('请输入电话号码'))
  } else if (!isvalidPhone(value)) {
    callback(new Error('请输入正确的11位手机号码'))
  } else {
    callback()
  }
}
const validMail = (rule, value, callback) => {
  if (value === '' || value === null) {
    callback(new Error('邮箱不能为空'))
  } else if (validEmail(value)) {
    callback()
  } else {
    callback(new Error('邮箱格式错误'))
  }
}
export default {
  name: 'Center',
  components: { updatePass },
  data() {
    return {
      show: false,
      Avatar: Avatar,
      activeName: 'first',
      saveLoading: false,
      headers: {
        Authorization: getToken()
      },
      form: {},
      rules: {
        nickName: [
          { required: true, message: '请输入用户昵称', trigger: 'blur' },
          {
            min: 2,
            max: 20,
            message: '长度在 2 到 20 个字符',
            trigger: 'blur'
          }
        ],
        phone: [{ required: true, trigger: 'blur', validator: validPhone }],
        email: [{ required: true, trigger: 'blur', validator: validMail }]
      }
    }
  },
  computed: {
    ...mapGetters(['user'])
  },
  created() {
    this.form = {
      id: this.user.id,
      nickName: this.user.nickName,
      gender: this.user.gender,
      phone: this.user.phone,
      email: this.user.email
    }
  },
  methods: {
    // toggleShow() {
    //   this.show = !this.show;
    // },
    handleClick(tab, event) {
      if (tab.name === 'second') {
        this.init()
      }
    },
    // beforeInit() {
    //   this.url = "api/logs/user";
    //   return true;
    // },
    // cropUploadSuccess(jsonData, field) {
    //   store.dispatch("GetInfo").then(() => {});
    // },
    doSubmit() {
      if (this.$refs['form']) {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.saveLoading = true
            editProfile(this.form)
              .then(() => {
                this.$notify({
                  title: '更新用户信息成功',
                  type: 'success'
                })
                store.dispatch('getInfo').then(() => {})
                this.saveLoading = false
              })
              .catch(() => {
                this.saveLoading = false
              })
          }
        })
      }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
.avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
}
.user-info {
  padding-left: 0;
  list-style: none;
  li {
    border-bottom: 1px solid #f0f3f4;
    padding: 11px 0;
    font-size: 13px;
  }
  .user-right {
    float: right;
    a {
      color: #317ef3;
    }
  }
}
</style>
