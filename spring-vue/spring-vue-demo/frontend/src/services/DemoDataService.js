import http from '../http-common'

class DemoDataService {

    create(demo) {
        return http.post('/demos', demo)
    }

    getAll() {
        return http.get('/demos')
    }

    get(id) {
        return http.get(`/demos/${id}`)
    }

    getByTitle(title) {
        return http.get(`/demos/title/${title}`)
    }

    update(id, demo) {
        return http.put(`/demos/${id}`, demo)
    }

    delete(id) {
        return http.delete(`/demos/${id}`)
    }

    deleteAll() {
        return http.delete('/demos')
    }
}

export default new DemoDataService()