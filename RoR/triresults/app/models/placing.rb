class Placing
    attr_accessor :name, :place
    
    def initialize name=nil, place=nil
        @name = name
        @place = place
    end
    
    def mongoize
        {:name=>@name, :place=>@place}
    end
    
    def self.mongoize object
        case object
        when nil then nil        
        when Hash then Placing.new(object[:name], object[:place]).mongoize 
        when Placing then object.mongoize
        else object
        end
    end
    
    def self.demongoize object
        case object
        when nil then nil
        when Hash then Placing.new(object[:name], object[:place])
        else object
        end
    end
    
    def self.evolve object
        mongoize object
    end
end