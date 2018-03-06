(function () {
    'use strict';

    angular
        .module('noteApp')
        .factory('NotesService', NotesService);

    NotesService.$inject = ['$http'];

    function NotesService($http) {

        var factory = {
        		getNotes: getNotes,
        };

        return factory;
        
        function getNotes(pagination) {
        	return $http({
                method: 'GET',
                url: 'note/',
                params: {
                	searchQuery: pagination.notes.searchQuery,
                	pageNumber: pagination.notes.pageNumber,
                	sortBy: pagination.notes.sortBy,
                	order: pagination.notes.order,
                	limit: pagination.itemsPerPage	
                }
            })
        }
    }
})();