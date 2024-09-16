<script>
  import DemoDataService from "../services/DemoDataService"

  export default {
    name: 'add-demo',
    data() {
      return {
        demo: {
          id: null,
          title: ''
        },
        submitted: false
      }
    },
    methods: {
      saveDemo() {
        var data = {
          title: this.demo.title
        }

        DemoDataService.create(data)
        .then(res => {
          console.log(res.data)
          this.demo.id = res.data.id
          this.submitted = true
        })
        .catch(err => {
          console.log(err)
        })
      },

      newDemo() {
        this.submitted = false
        this.demo = {}
      },

      returnToList() {
        this.$router.push({ name: 'demos' })
      }
    }
  }
</script>

<template>
  <div class="submit-form">
    <div v-if="!submitted">
      <div class="form-group">
        <label for="title">Title</label>
        <input v-model="demo.title" type="text" class="form-control" name="title" id="title" required >
      </div>

      <button @click="saveDemo" class="btn btn-primary">Submit</button>
    </div>
    <div v-else>
      <h4>You submitted successfully</h4>
      <button class="btn btn-success" @click="newDemo">Add Another</button>
      <button class="btn btn-primary" @click="returnToList">Return to List</button>
    </div>
  </div>
</template>

<style scoped>
  .submit-form {
    max-width: 300px;
    margin: auto;
  }
</style>