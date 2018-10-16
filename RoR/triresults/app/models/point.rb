class Point
    attr_accessor :longitude, :latitude
    
    def initialize lng=nil, lat=nil
        @longitude = lng
        @latitude = lat
    end
    
    def mongoize
        {:type=>"Point", :coordinates=>[@longitude, @latitude]}
    end
    
    def self.mongoize object
        case object
        when nil then nil        
        when Hash then
            if object[:type] == 'Point'
                Point.new(object[:coordinates][0], object[:coordinates][1]).mongoize
            else
                Point.new(object[:lng], object[:lat]).mongoize
            end
        when Point then object.mongoize
            
        else object
        end
    end
    
    def self.demongoize object
        case object
        when nil then nil
        when Hash then 
            if object[:type] == 'Point'
                Point.new(object[:coordinates][0], object[:coordinates][1])
            else
                object
            end
        else object        
        end
    end   

    def self.evolve object
        mongoize object
    end
end