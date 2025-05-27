<template>
    <div class="search-box">
      <input 
        type="text"
        v-model="searchQuery"
        placeholder="搜索内容"
        class="search-input"
        @change="search"
      />
    </div>
</template>
  
<script>
  export default {
    // props:{
    //   searchBox_visible:Boolean
    // },
    name: 'SearchBox',
    data() {
      return {
        searchQuery:'',
        searchBox_visible:true
      }
    },
    methods:{
      search(){
        this.$emit('search',this.searchQuery)
      },
    },
  }
</script>
  
<style scoped>
  
  .search-box{
    height:50px;
  }
  .search-input {
    height: 40px;
    width: 240px;
    padding: 0 16px;
    border-radius: 8px;
    border: 1px solid #e5e7eb;
    font-size: 14px;
    outline: none;
    transition: all 0.2s ease;
  }
  
  .search-input:focus {
    border-color: #3B82F6;
    box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
  }
  
  @media (max-width: 768px) {
    .search-input {
      width: 160px;
    }
  }
</style>