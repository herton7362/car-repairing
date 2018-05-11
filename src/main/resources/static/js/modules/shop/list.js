require(['jquery', 'vue', 'messager', 'utils'], function($, Vue, messager, utils) {
    new Vue({
        el: '#content',
        data: {
            crudgrid: {
                $instance:{},
                queryParams: {
                    name: ''
                },
                columns: [
                    {field:'name', title:'名称'},
                    {field:'address', title:'地址'}
                ]
            },
            formData: {
                id: null,
                name: null,
                address: null,
                styleImages: []
            }
        },
        methods: {

        },
        mounted: function() {
            var self = this;
            this.crudgrid.$instance.load();
        }
    });
});
