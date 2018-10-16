module Api
    class RacesController < ApplicationController
        
        def index
            if !request.accept || request.accept == "*/*"
                render plain: "/api/races, offset=[#{params[:offset]}], limit=[#{params[:limit]}]"
            else
                
            end
        end
        
        def show
            if !request.accept || request.accept == "*/*"
                render plain: "/api/races/#{params[:id]}"
            else
                @race = Race.find(params[:id]) 
                render action: :show
            end    
        end
        
        def create
            if !request.accept || request.accept == "*/*"
                if params[:race].nil?
                    render plain: "", status: :ok
                else
                    render plain: params[:race][:name]
                end
            else
                if !params[:race].nil?
                    Race.create(race_params)
                    render plain: race_params[:name], status: :created
                end
            end
        end
        
        def update
            race = Race.find(params[:id])
            race.update(race_params)
            render json: race
        end
        
        def destroy
            race = Race.find(params[:id])
            race.destroy
            render :nothing=>true, :status=>:no_content
        end
        
        rescue_from Mongoid::Errors::DocumentNotFound do |exception|
            if !request.accept || request.accept == "*/*"
                render plain: "woops: cannot find race[#{params[:id]}]", status: :not_found
            else
                render :status=> :not_found, :template=>"api/races/error_msg", :locals=>{ :msg=>"woops: cannot find race[#{params[:id]}]"}
            end
        end
        
        rescue_from ActionView::MissingTemplate do |exception|
            Rails.logger.debug exception
            render plain: "woops: we do not support that content-type[#{request.accept}]", status: :unsupported_media_type
        end
        
        private
            def race_params
                params.require(:race).permit(:name, :date)                
            end
    end
end