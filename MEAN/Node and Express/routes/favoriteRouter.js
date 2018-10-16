var express = require('express');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');

var Favorites = require('../models/favorites');
var Verify = require('./verify');

var favoriteRouter = express.Router();
favoriteRouter.use(bodyParser.json());

//Find the favorites of the current user
favoriteRouter.route('/')
.get(Verify.verifyOrdinaryUser, function (req, res, next) {
    Favorites.find({postedBy: req.decoded._doc._id})
        .populate('postedBy')
        .populate('dishes')
        .exec(function (err, favorite) {
        if (err) throw err;
        res.json(favorite);
    });
})

//1) Check whether the favorite document for the current user exists
//2) If not, go to step 3, else go to step 4
//3) Create a new favorite document(upsert)
//4) Add the new dish only if it hasn't been inserted before(addToSet)
.post(Verify.verifyOrdinaryUser, function (req, res, next) {
    Favorites.findOneAndUpdate({postedBy: req.decoded._doc._id}, {$addToSet: {dishes: req.body}}, {upsert: true, new: true}, function(err, favorite) {
        if (err) throw error;
        console.log('Dish added to favorite!');
        res.json(favorite);
    })
})

//Delete the entire favorite document of current user if it exists
.delete(Verify.verifyOrdinaryUser, function (req, res, next) {
    Favorites.findOneAndRemove({postedBy: req.decoded._doc._id}, {new: false}, function (err, favorite) {
        if (err) throw err;
        res.json(favorite);
    });
});

//Delete a particular dish with dishObjectId from the favorite document of current user
favoriteRouter.route('/:dishObjectId')
.delete(Verify.verifyOrdinaryUser, function (req, res, next) {
    Favorites.findOneAndUpdate({postedBy: req.decoded._doc._id}, {$pull: {dishes: req.params.dishObjectId}}, {new: true}, function (err, resp) {
        if (err) throw err;
        console.log('Removed dish ' + req.params.dishId + ' from favorites.');
        res.json(resp);
    })
})

module.exports = favoriteRouter;
