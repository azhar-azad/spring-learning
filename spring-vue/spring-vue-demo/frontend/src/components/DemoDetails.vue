<script>
  import DemoDataService from "../services/DemoDataService";

  export default {
    name: 'demo-details',

    data() {
      return {
        currentDemo: null,
        message: ''
      }
    },

    methods: {
      getDemo(id) {
        DemoDataService.get(id)
            .then(response => {
              console.log(response.data)
              this.currentDemo = response.data
            })
            .catch(e => {
              console.log(e)
            })
      },

      updateDemo() {
        DemoDataService.update(this.currentDemo.id, this.currentDemo)
            .then(response => {
              console.log(response.data)
              this.message = 'Demo was updated successfully!'
            })
            .catch(e => {
              console.log(e)
            })
      },

      deleteDemo() {
        DemoDataService.delete(this.currentDemo.id)
            .then(response => {
              console.log(response.data)
              this.$router.push({ name: 'demos' })
            })
            .catch(e => {
              console.log(e)
            })
      }
    },

    mounted() {
      this.message = ''
      this.getDemo(this.$route.params.id)
    }
  }
</script>

<template>
  <div v-if="currentDemo" class="edit-form">
    <h4>Demo</h4>
    <form>
      <div class="form-group">
        <label for="title">Title</label>
        <input v-model="currentDemo.title" type="text" class="form-control" id="title">
      </div>
    </form>

    <button @click="deleteDemo" class="badge badge-danger mr-2">Delete</button>
    <button @click="updateDemo" type="submit" class="badge badge-success">Update</button>
    <p>{{ message }}</p>
  </div>
  <div v-else>
    <br>
    <p>Please click on a Demo...</p>
  </div>
</template>

<style scoped>
  .edit-form {
    max-width: 300px;
    margin: auto;
  }
</style>