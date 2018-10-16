class Recipe
	include HTTParty

	key_value = ENV['FOOD2FORK_KEY'] || '9290ec11ed4c466cf0a9a704c5513c6e'
	hostport = ENV['FOOD2FORK_SERVER_AND_PORT'] || 'food2fork.com'
	base_uri "http://#{hostport}/api"
	default_params key: key_value #, q: "/search"
	format :json
	
	def self.for term
		get("/search", query: { q: term})["recipes"]
	end
end