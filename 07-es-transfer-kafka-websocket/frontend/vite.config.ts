import { defineConfig } from 'vite'
import { svelte } from '@sveltejs/vite-plugin-svelte'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [svelte({
    compilerOptions: {
      customElement: true
    }}
  )],
  build: {
    rollupOptions: {
      output: {
        assetFileNames: (assetInfo) => {
          return `transfer/assets/[name]-[hash][extname]`;
        },
        chunkFileNames: 'transfer/assets/[name]-[hash].js',
        entryFileNames: 'transfer/assets/[name]-[hash].js',
      },
    },
  },
  server: {
    host: true,
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://10.0.2.2:8080',
        rewrite: path => path.replace('/api', ''),
        changeOrigin: true,
        secure: false
      }
    }
  }
})
