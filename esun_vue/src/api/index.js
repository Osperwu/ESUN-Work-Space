// src/api/index.js
import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api', // 可以使用 .env 設定
  withCredentials: true // 用於管理 session 或 cookies
});

// 儲存 token
let token = null;

export function setToken(newToken) {
  token = newToken;
}

// 請求攔截器：在每次發出請求時將 token 加入標頭
api.interceptors.request.use(config => {
  if (token) {
    config.headers['Authorization'] = token;
  }
  return config;
}, error => Promise.reject(error));

export default api;