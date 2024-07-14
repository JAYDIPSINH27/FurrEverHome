import { defineConfig, loadEnv} from 'vite'
import react from '@vitejs/plugin-react'
// const env = loadEnv(mode, process.cwd(), '');

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {

  const env = loadEnv(mode, process.cwd(), '')


  
  return {
    plugins: [react()],
  preview: {
    port: env.FRONTEND_APP_PORT,
    // strictPort: true,
   },
   server: {
    port: env.SERVER_PORT,
    strictPort: true,
    host: true,
    // origin: "http://0.0.0.0:5173",
   },
  };
})