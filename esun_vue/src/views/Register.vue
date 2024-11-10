<template>
  <div>
    <h2>註冊</h2>
    <form @submit.prevent="register">
      <input v-model="userName" placeholder="使用者名稱" required />
      <br>
      <input v-model="email" type="email" placeholder="電子郵件" required />
      <br>
      <input v-model="phoneNumber" placeholder="手機號碼" required />
      <br>
      <input type="password" v-model="password" placeholder="密碼" required />
      <br>
      <button type="submit">註冊</button>
    </form>
  </div>
</template>

<script>
import api from '../api';

export default {
  data() {
    return {
      userName: '',    // 使用者名稱
      email: '',       // 電子郵件
      phoneNumber: '', // 手機號碼
      password: ''     // 密碼
    };
  },
  methods: {
    async register() {
      try {
        const response = await api.post('/user/register', {
          user_id: 0,
          userName: this.userName,
          email: this.email,
          phoneNumber: this.phoneNumber,
          password: this.password
        });
        
        // 註冊成功訊息顯示
        alert('註冊成功！');
             // 在 alert 確認後跳回首頁
      this.$router.push('/');
      } catch (error) {
        // 根據錯誤碼顯示錯誤訊息
        let errorMessage = '網路忙線中，未知錯誤';
        if (error.response && error.response.data && error.response.data.rm) {
          errorMessage = '註冊失敗：' + error.response.data.rm;
        } else if (error.response && error.response.data && error.response.data.message) {
          errorMessage = '註冊失敗：' + error.response.data.message;
        }
        
        // 顯示失敗訊息
        alert(errorMessage);
      }
    }
  }
};
</script>