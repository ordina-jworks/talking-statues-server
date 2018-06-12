
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("images/" + m.getPicture());
        return monumentRepository.findAll();
    }

    Monument addMonument(Monument monument){
        if(monument.getId()==null){
            return monumentRepository.save(monument);
        }else {
            throw new RuntimeException("the new monument already has an id");
        }
    }

    void putMonument(String id, Monument monument){
        monumentRepository.findById(id).ifPresent(mon-> {
            monument.setId(id);
            monumentRepository.save(monument);
        });
    }

    void saveImage(InputStream stream, String id){
        gridFsTemplate.store(stream, id, MediaType.IMAGE_JPEG_VALUE);
    }

    GridFsResource getImageForMonumentId(String id){
        return gridFsTemplate.getResource(id);
    }
}
