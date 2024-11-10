<template>
    <div>
      <h2>登入</h2>
      <form @submit.prevent="login">
        <input v-model="phoneNumber" placeholder="手機號碼" required />
        <br>
        <input type="password" v-model="password" placeholder="密碼" required />
        <br>
        <button type="submit">登入</button>
      </form>
      <!-- <p>{{ message }}</p> -->
    </div>
  </template>
  
  <script>
import api, { setToken } from '../api';

export default {
  data() {
    return {
      phoneNumber: '',
      password: '',
      message: ''
    };
  },
  methods: {
    async login() {
      try {
        const response = await api.post('/user/login', {
          phoneNumber: this.phoneNumber,
          password: this.password
        });
        
        // this.message = '登入成功！';
        
        // 將 token 設置到 axios 頭部
        setToken(response.data.token);
        alert('登入成功 ！');
        // 在 alert 確認後跳回首頁
      this.$router.push('/');
      } catch (error) {
        // 根據錯誤碼顯示錯誤訊息
        let errorMessage = '網路忙線中，未知錯誤';
        if (error.response && error.response.data && error.response.data.rm) {
          errorMessage = '登入失敗：' + error.response.data.rm;
        } else {
          errorMessage = '登入失敗：' + error.response.data.message || '未知錯誤';
        }

        // 顯示失敗訊息
        alert(errorMessage);
      }
    }
  }
};
</script>