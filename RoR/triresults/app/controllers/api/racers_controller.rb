module Api
    class RacersController < ApplicationController
        def index
            if !request.accept || request.accept == "*/*"
                render plain: "/api/racers"
            else
                
            end
        end
        
        def show
            if !request.accept || request.accept == "*/*"
                render plain: api_racer_path(params[:id])
            else
                
            end
        end
    end
end