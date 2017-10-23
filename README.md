# JavaFX with JBox2D integration

This small programs generates random JBox2D shapes (circle, rectangles and polygons)
which react with their environment. 

Thanks to the great work of Daniel Shiffman (http://www.shiffman.net) I finally
was able to integrate JBox2D physic library (https://github.com/jbox2d/jbox2d)
into JavaFX. 

The java class "Box2DProcessing" of the original example programs of Daniel Shiffman 
based on processing (https://processing.org/) made the cumbersome work of translating
JBox2D coordinates into pixel coordinates quite simple.

I also enclosed is the PVector class from processing (https://www.processing.org/) 
and a small helper (Util) class for eg. creating random numbers.

