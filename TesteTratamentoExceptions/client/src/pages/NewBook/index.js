import React, {useState, useEffect} from 'react';
import {useHistory, Link, useParams} from 'react-router-dom';
import {FiArrowLeft} from 'react-icons/fi';
import api from '../../services/api';

import './styles.css';
import logoImage from '../../assets/logo.svg';

function NewBook() {
    const [id, setId] = useState('');
    const [author, setAuthor] = useState('');
    const [title, setTitle] = useState('');
    const [launchDate, setLaunchDate] = useState('');
    const [price, setPrice] = useState('');
    const history = useHistory();

    //Recebe parâmetro da rota. O nome da CONST deve ser igual ao definido na rota.
    const {bookId} = useParams();

    //Para bindar variáveis dentro de strings, definir a string entre crases.
    const header = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem('accessToken')}`
        }
    };

    //O segundo parâmetro é um array que indica que a página deverá monitorar os states nele contidos.
    //Caso um dos states seja alterado, o efeito é disparado.
    useEffect(() => {
        //Para bindar variáveis dentro de strings, definir a string entre crases.
        const header = {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('accessToken')}`
            }
        };
        
        //Método que implementa o carregamento dos dados de um livro para edição.
        async function loadBook() {
            try {
                const response = await api.get(`api/book/v1/${bookId}`, header);

                //Pega os 10 primeiro caracteres da data, até a letra "T", para que o componente de calendário da página possa entender a data que está voltando do webservice.
                let adjustedDate = response.data.launchDate.split("T", 10)[0];
                setId(response.data.id);
                setTitle(response.data.title);
                setAuthor(response.data.author);
                setPrice(response.data.price);
                setLaunchDate(adjustedDate);
                
            } catch (err) {
                alert('Erro na busca dos dados do livro. Tente novamente...');
                history.push('/books');
            }
        };

        if (bookId === '0') return;
        else loadBook();
    }, [bookId]);

    async function saveOrUpdate(e) {
        //A linha abaixo é um evento que previne o recarregamento da página no submit.
        //Isso é necessário porque em aplicações Single Page Application, não se deve recarregar a página toda.
        e.preventDefault();

        const data = {
            author,
            launchDate,
            price,
            title
        };

        try {
            //Comando await indica que deve aguardar o retorno da resposta do POST para passar para a próxima linha.
            //Não precisa atribuir o retorno de api.post para uma CONST se o retorno não for usado na página.
            //O React sempre executa uma requisição prévia para verificar se a requisição que será executada na sequência tem permissão na API (React Preflight Request).
            //Assim, a API deve ser configurada para permitir essa requisição prévia.
            if (bookId === '0') {
                await api.post('api/book/v1', data, header);
            } else {
                data.id = id;
                await api.put('api/book/v1', data, header);
            }

            //Em caso de sucesso no cadastro, volta para página de listagem de livros.
            history.push('/books');

        } catch (err) {
            alert("Erro ao cadastrar o livro. Tente novamente...");
        }
    }

    return (
        <div className="new-book-container">
            <div className="content">
                <section className="form">
                    <img src={logoImage} alt="Almussio" />
                    <h1>{(bookId === '0') ? 'Cadastrar novo livro' : 'Alterar cadastro do livro'}</h1>
                    <p>Informe os dados do livro e clique em {(bookId === '0') ? 'Adicionar' : 'Alterar'}</p>
                    <Link className="back-link" to="/books">
                        <FiArrowLeft size={16} color="#251FC5" />
                        Página inicial
                    </Link>
                </section>
                <form onSubmit={saveOrUpdate}>
                    <input 
                        placeholder="Título" 
                        value={title}
                        onChange={e => setTitle(e.target.value)} 
                    />
                    <input 
                        placeholder="Autor" 
                        value={author}
                        onChange={e => setAuthor(e.target.value)} 
                    />
                    <input 
                        type="date" 
                        value={launchDate}
                        onChange={e => setLaunchDate(e.target.value)} 
                    />
                    <input 
                        placeholder="Preço"
                        value={price} 
                        onChange={e => setPrice(e.target.value)} 
                    />

                    <button type="submit" className="button">{(bookId === '0') ? 'Adicionar' : 'Alterar'}</button>
                </form>
            </div>
        </div>  
    );
}

export default NewBook;