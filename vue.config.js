const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
    '/back': {
    target: 'http://localhost:8081',
    changeOrigin: true,
    pathRewrite: {
    '^/back': ''
    }
    }
    }
    }
})
