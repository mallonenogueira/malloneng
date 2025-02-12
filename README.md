## Teste técnico: Backend Software Developer

- By: Mallone
- [Repositório Github](https://github.com/mallonenogueira/malloneng)
- [Linkedin | Mallone](https://www.linkedin.com/in/mallone/)

#### Rodando

```
docker build . -t malleng/backend

docker run -e BASE_URL=http://....malleng.com/ -p 4567:4567 --rm malleng/backend
```

#### Variáveis de ambiente

```
BASE_URL=
NUM_THREAD_EVENT=
NUM_THREAD_REQUESTS=
```

* BASE_URL: Url que o processo ira buscar os dados
* NUM_THREAD_EVENT: Número de thread para o subscriber (Eventos em paralelo)
* NUM_THREAD_REQUESTS: Número de threads que o crawler utilizara (Os crawlers são iniciados pelas threads de eventos `(NUM_THREAD_EVENT * NUM_THREAD_REQUESTS)`) 

#### As demais orientações estão no PDF fornecido pela empresa
