<script>
  import DemoDataService from "../services/DemoDataService";

  export default {
    name: 'demo-list',

    data() {
      return {
        demos: [],
        currentDemo: null,
        currentIndex: -1,
        title: ''
      }
    },

    methods: {
      retrieveDemos() {
        DemoDataService.getAll()
            .then(response => {
              console.log(response.data)
              this.demos = response.data
            })
            .catch(e => {
              console.log(e)
            })
      },

      refreshList() {
        this.retrieveDemos()
        this.currentDemo = null
        this.currentIndex = -1
      },

      setActiveDemo(demo, index) {
        this.currentDemo = demo
        this.currentIndex = demo ? index : -1
      },

      removeAllDemos() {
        DemoDataService.deleteAll()
            .then(response => {
              console.log(response.data)
              this.refreshList()
            })
            .catch(e => {
              console.log(e)
            })
      },

      searchTitle() {
        DemoDataService.getByTitle(this.title)
            .then(response => {
              console.log(response.data)
              this.demos = response.data
              this.setActiveDemo(null)
            })
            .catch(e => {
              console.log(e)
            })
      }
    },

    mounted() {
      this.retrieveDemos()
    }
  }
</script>

<template>
  <div class="list row">
    <div class="col-md-8">
      <div class="input-group mb-3">
        <input v-model="title" type="text" class="form-control" placeholder="Search by title">
        <div class="input-group-append">
          <button @click="searchTitle" class="btn btn-outline-secondary" type="button">Search</button>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <h4>Demo List</h4>
      <ul class="list-group">
        <li :class="{ active: index == currentIndex }" class="list-group-item"
          v-for="(demo, index) in demos" :key="index" @click="setActiveDemo(demo, index)">
          {{ demo.title }}
        </li>
      </ul>
      <button @click="removeAllDemos" class="m-3 btn btn-sm btn-danger">Remove All</button>
    </div>
    <div class="col-md-6">
      <div v-if="currentDemo">
        <h4>Demo</h4>
        <div>
          <label><strong>Title:</strong></label> {{ currentDemo.title }}
        </div>
        <router-link :to="'/demos/' + currentDemo.id" class="badge badge-warning">Edit</router-link>
      </div>
      <div v-else>
        <br>
        <p>Please click on a Demo...</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
  .list {
    text-align: left;
    max-width: 750px;
    margin: auto;
  }
</style>