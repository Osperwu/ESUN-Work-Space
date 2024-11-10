<template>
    <div>
      <h2>新增發文</h2>
      <form @submit.prevent="createPost">
        <textarea v-model="content" placeholder="發文內容" required></textarea>
        <input v-model="imageUrl" placeholder="圖片 URL" />
        <button type="submit">發佈</button>
      </form>
    </div>
  </template>
  
  <script>
  import api from '../api';
  
  export default {
    data() {
      return {
        content: '',
        imageUrl: ''
      };
    },
    methods: {
      async createPost() {
        try {
          const response = await api.post('/posts/create', {
            content: this.content,
            imageUrl: this.imageUrl
          });
          alert('發文成功');
          // 在 alert 確認後跳回首頁
      this.$router.push('/');
        } catch (error) {
            // 設置錯誤訊息為 API 返回的 rm 值
        const errorMessage = error.response && error.response.data && error.response.data.rm
          ? error.response.data.rm
          : '發文失敗';
        alert(errorMessage);
        console.error('發文失敗:', errorMessage);
        }
      }
    }
  };
  </script>