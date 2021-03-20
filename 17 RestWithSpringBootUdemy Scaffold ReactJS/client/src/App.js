import React, {useState} from 'react';
import Header from './Header'

function App() {
  //State - ReactJS não permite uso de variáveis comuns do JavaScript.
  //Por questões de performance, ele trabalha com o conceito de estados imutáveis.
  //E por ser imutável, a declaração do estado sempre requer um array de valor e uma função de alteração do valor, conforme abaixo:
  //cont [value, changeValueFunction]
  const [counter, setCounter] = useState(0);

  function increment() {
    setCounter(counter + 1);
  }

  function reset() {
    setCounter(0);
  }

  return (
    //JSX = JavaScript XML
    //Componentes javascript que contém o HTML da aplicação.

    //Passagem de propriedades para o componente - Forma 1
    //<Header title="Client REST Udemy - Properties" />
    //Nesta forma, o componente recebe a propriedade como um parâmetro:
    //function Component(props)...

    //Passagem de propriedades para o componente - Forma 2
    //<Header>
    //  Client REST Udemy - Properties
    //</Header>
    //Nesta forma, o componente recebe o conteúdo filho da tag, através de uma referência aos filhos:
    //function Component({children})...

    <div>
      <Header>
        <h1>Udemy REST Client</h1>
        Counter: {counter}
      </Header>
      <button onClick={increment}>Add</button>
      <button onClick={reset}>Reset</button>
    </div>
  );
}

export default App;
