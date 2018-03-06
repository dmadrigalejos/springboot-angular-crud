(function () {
    'use strict';

    angular
        .module("noteApp")
        .config(stateConfig)

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
    	
        $stateProvider
        .state('dashboard.notes',{
            url: '/notes',
            controller: 'NotesController',
            controllerAs: 'vmNotes',
            templateUrl: 'js/entities/notes/notes.html'
        })
    }
})();



