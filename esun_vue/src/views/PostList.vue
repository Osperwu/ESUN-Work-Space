<template>
  <div>
    <h2>所有發文</h2>
    
    <!-- 錯誤訊息顯示 -->
    <p v-if="errorMessage" style="color: red; font-weight: bold;">錯誤：{{ errorMessage }}</p>

    <!-- 新增發文按鈕 -->
    <button @click="goToCreatePost" class="create-post-btn">新增發文</button>

    <div class="post-grid">
      <div 
        class="post-item" 
        v-for="post in posts" 
        :key="post.postId"
      >
        <p><strong>文章ID:</strong> {{ post.postId }}</p>
        <p><strong>使用者ID:</strong> {{ post.userId }}</p>
        <p><strong>內容:</strong> {{ post.content }}</p>
        <p><strong>發佈時間:</strong> {{ formatDate(post.createdAt) }}</p>
        <img :src="post.image" v-if="post.image" alt="Post image" />
        
        <!-- 刪除按鈕，僅顯示於原作者 -->
        <button v-if="post.userId === currentUserId" @click="deletePost(post.postId)">刪除發文</button>
        
        <!-- 查詢留言按鈕 -->
        <button @click="fetchComments(post.postId)">查看留言</button>
        
        <!-- 新增留言按鈕，僅顯示已登入的用戶 -->
        <button v-if="currentUserId" @click="openCommentForm(post.postId)">新增留言</button>
        
        <!-- 新增留言表單 -->
        <div v-if="post.postId === commentFormPostId">
          <textarea v-model="newComment" placeholder="輸入留言..." rows="3" cols="40"></textarea>
          <button @click="addComment(post.postId)">送出留言</button>
        </div>
        
        <!-- 顯示留言列表 -->
        <div v-if="comments[post.postId] && comments[post.postId].length > 0">
          <h3>留言</h3>
          <ul>
            <li v-for="comment in comments[post.postId]" :key="comment.commentId">{{ comment.content }}</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '../api';
import { useRouter } from 'vue-router';

export default {
  data() {
    return {
      posts: [],               // 發文列表
      comments: {},            // 使用 postId 為鍵，儲存各發文的留言
      currentUserId: 4,        // 假設當前用戶ID為 4（實際應該從登錄系統中獲取）
      newComment: '',          // 用戶輸入的新留言內容
      commentFormPostId: null, // 顯示留言表單的發文ID
      errorMessage: ''         // 錯誤訊息
    };
  },
  created() {
    // 初始化頁面時獲取所有發文
    this.fetchPosts();
  },
  methods: {
    async fetchPosts() {
      try {
        const response = await api.get('/posts/getAll');
        this.posts = response.data;
      } catch (error) {
        // 如果 API 返回錯誤，設置 errorMessage
        this.errorMessage = error.response.data.rm || '獲取發文失敗';
      }
    },
    // 跳轉到發文頁面
    goToCreatePost() {
      this.$router.push('/posts/create');
    },
    async fetchComments(postId) {
      try {
        const response = await api.get(`/comments/post/${postId}`);
        if (response && response.data) {
          this.comments[postId] = response.data;
        } else {
          this.errorMessage = '未收到有效的留言數據';
        }
      } catch (error) {
        // 設置錯誤訊息為 API 返回的 rm 值
        const errorMessage = error.response && error.response.data && error.response.data.rm
          ? error.response.data.rm
          : '查詢留言失敗';
        alert(errorMessage);
        console.error('查詢留言失敗：', errorMessage);
      }
    },
    async deletePost(postId) {
      try {
        await api.delete(`/posts/${postId}`);
        this.posts = this.posts.filter(post => post.postId !== postId);
      } catch (error) {
        const errorMessage = error.response && error.response.data && error.response.data.rm
          ? error.response.data.rm
          : '刪除發文失敗';
        alert(errorMessage);
        console.error('刪除發文失敗：', errorMessage);
      }
    },
    openCommentForm(postId) {
      this.commentFormPostId = postId;
      this.newComment = '';
    },
    async addComment(postId) {
      if (!this.newComment) {
        alert('留言內容不可為空');
        return;
      }
      try {
        await api.post('/comments/create', {
          postId: postId,
          content: this.newComment
        });
        this.newComment = '';
        this.commentFormPostId = null;
        alert('留言已送出');
        this.fetchComments(postId);
      } catch (error) {
        const errorMessage = error.response && error.response.data && error.response.data.rm
          ? error.response.data.rm
          : '新增留言失敗';
        alert(errorMessage);
        console.error('新增留言失敗：', errorMessage);
      }
    },
    formatDate(date) {
      const options = { year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric' };
      return new Date(date).toLocaleDateString(undefined, options);
    }
  }
};
</script>

<style scoped>
/* 新增發文按鈕 */
.create-post-btn {
  background-color: rgba(255, 204, 0, 0.596); /* 紅色 */
  color: white;
  border: none;
  border-radius: 5px;
  padding: 8px 15px;
  cursor: pointer;
  margin-bottom: 20px; /* 讓按鈕與發文列表分開一些 */
}

.create-post-btn:hover {
  background-color: darkred; /* 滑鼠懸停時的顏色 */
}

/* 3x3 排列的 CSS Grid */
.post-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  padding: 20px;
}

.post-item {
  border: 1px solid #ddd;
  padding: 15px;
  border-radius: 8px;
  background-color: #f9f9f9;
  color: black;
}

button {
  margin-top: 10px;
  padding: 8px 15px;
  background-color: #03802fe1;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

h3 {
  margin-top: 20px;
}

ul {
  list-style: none;
  padding-left: 0;
}

li {
  margin: 5px 0;
  padding: 8px;
  background-color: #333;
  color: white;
  border-radius: 5px;
}

textarea {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ddd;
  margin-top: 10px;
}
</style>