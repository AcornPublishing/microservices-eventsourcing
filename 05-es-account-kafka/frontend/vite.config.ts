import { defineConfig } from 'vite'
import { svelte } from '@sveltejs/vite-plugin-svelte'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [svelte()],
  build: {
    rollupOptions: {
      output: {
        assetFileNames: (assetInfo) => {
          // let extType = assetInfo.name.split('.').at(1);
          // if (/png|jpe?g|svg|gif|tiff|bmp|ico/i.test(extType)) {
          //   extType = 'img';
          // }
          return `account/assets/[name]-[hash][extname]`;
        },
        chunkFileNames: 'account/assets/[name]-[hash].js',
        entryFileNames: 'account/assets/[name]-[hash].js',
      },
    },
  },
  server: {
    host: true,
    port: 3001,
    proxy: {
      '/api': {
        target: 'http://10.0.2.4:8080',
        rewrite: path => path.replace('/api', ''),
        changeOrigin: true,
        secure: false
      }
    }
  }
})
