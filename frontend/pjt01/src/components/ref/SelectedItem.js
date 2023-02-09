import React from 'react';
import "./css/SelectedItem.css";
import { useState } from "react";


function SelectedItem(props) {
  const [colorstate, setcolorstate] = useState("");

  console.log(colorstate)
  const styleclick = {
    background : "#9CCBFE",
    color: "white"
  }
  const styleclick2 = {
    background : "white",
    color: "#6C6C6C"
  }
  return(
      <div>
        {colorstate ? (
        <div style={styleclick} className='itemlistbox'>
            < div  key={props.index} className='select_item_text'  
              onClick={() => {
                setcolorstate(null);
                props.deleteItem(props.item.itemid);
            }}
            >
            <div className="itemText">{props.item.itemid}</div>
          </div>
        </div>
        ) : (
        <div style={styleclick2}  className='itemlistbox'>
        <div key={props.index} className='select_item_text'
          onClick={() => {
            setcolorstate(1);
            props.addItem(props.item.itemid);
            
        }}>
          <div className="itemText">{props.item.itemid}</div>
        </div >  
      </div>

      )}
      </div>


  );
}

export default SelectedItem;


  