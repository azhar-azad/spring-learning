import axios from "axios"

export default axios.create({
    // baseURL: process.env.VUE_APP_BASE_URL,
    baseURL: 'http://localhost:8080/api/v1',
    headers: {
        'Content-Type': 'application/json'
    }
})