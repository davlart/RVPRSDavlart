 
var myApp=angular.module('myApp',[]);

myApp.controller('registrationController', function($scope) {
 
  $scope.registrations = [{
        date: 'Август 2016',
        day: '2 дня',
        coldwater: 150,
        hotwater: 300,
        electricity: 1120,
        gas: 250
    },{
      date: 'Сентябрь 2016',
      day: '32 дня',
      coldwater: 550,
      hotwater: 100,
      electricity: 2120,
      gas: 750
  },{
      date: 'Октябрь 2016',
      day: '62 дня',
      coldwater: 250,
      hotwater: 600,
      electricity: 3120,
      gas: 250
  },{
      date: 'Ноябрь 2016',
      day: '92 дня',
      coldwater: 170,
      hotwater: 320,
      electricity: 1150,
      gas: 250
  }]
});

 