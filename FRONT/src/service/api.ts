import axios from "axios";
import { useAuthStore } from "@/stores/token";

const api = axios.create({
  baseURL: "//jhony4979.c44.integrator.host:15792/api/v1",
});

// Interceptor para adicionar o token automaticamente
api.interceptors.request.use(
  (config) => {
    const token = useAuthStore().token;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;
