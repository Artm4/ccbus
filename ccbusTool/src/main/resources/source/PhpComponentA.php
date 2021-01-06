<?php
class VArray implements Iterator,ArrayAccess
{
    const TYPE_INT='int';
    const TYPE_STRING='string';

    function __construct($type)
    {

    }
    function setType()
    {

    }
    function get($index)
    {

    }

    public function current()
    {
        // TODO: Implement current() method.
    }

    public function next()
    {
        // TODO: Implement next() method.
    }

    public function key()
    {
        // TODO: Implement key() method.
    }

    public function valid()
    {
        // TODO: Implement valid() method.
    }

    public function rewind()
    {
        // TODO: Implement rewind() method.
    }

    public function offsetExists($offset)
    {
        // TODO: Implement offsetExists() method.
    }

    public function offsetGet($offset)
    {
        // TODO: Implement offsetGet() method.
    }

    public function offsetSet($offset, $value)
    {
        // TODO: Implement offsetSet() method.
    }

    public function offsetUnset($offset)
    {
        // TODO: Implement offsetUnset() method.
    }


}

interface VModel
{

}

class MyPayload implements VModel
{
    public $val=0;
    public $str='';

    /**
     * MyPayload constructor.
     * @param int $val
     * @param string $str
     */
    public function __construct(int $val, string $str)
    {
        $this->val = $val;
        $this->str = $str;
    }


}

class MyResult implements VModel
{
    public $val=0;
}

class VWorker
{
    function __construct($callback)
    {

    }
}

class AngularComponent
{
    public $someVal="";
    private $result=1;


    function onClick()
    {
        $payload=new VArray(MyPayload::class);

        $payload[]=new MyPayload();
        $worker=new VWorker(
            function($payload){
                this.result(new MyResult(1));
            }
        );
        $result=$worker.get();
        $this->someVal=$result->val; // generate asynch call on client

        $some=1.0;
        $a=1;
    }

    function run()
    {

    }
}
?>