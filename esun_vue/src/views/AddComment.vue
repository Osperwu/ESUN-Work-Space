<template>
    <div>
      <h2>新增留言</h2>
      <form @submit.prevent="addComment">
        <textarea v-model="content" placeholder="留言內容" required></textarea>
        <button type="submit">提交留言</button>
      </form>
    </div>
  </template>
  
  <script>
  import api from '../api';
  
  export default {
    props: {
      postId: Number
    },
    data() {
      return {
        content: ''
      };
    },
    methods: {
      async addComment() {
        try {
          const response = await api.post('/comments/create', {
            content: this.content,
            postId: this.postId
          });
          console.log('留言成功');
        } catch (error) {
          console.error('留言失敗：', error.response.data.message);
        }
      }
    }
  };
  </script>